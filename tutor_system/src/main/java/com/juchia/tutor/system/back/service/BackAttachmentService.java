package com.juchia.tutor.system.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.juchia.tutor.common.util.FileUtils;
import com.juchia.tutor.system.back.entity.bo.AttachmentBO1;
import com.juchia.tutor.system.back.entity.query.AttachmentQuery1;
import com.juchia.tutor.system.common.entity.po.Attachment;
import com.juchia.tutor.system.common.exception.BusinessException;
import com.juchia.tutor.system.common.mapper.AttachmentMapper;
import com.juchia.tutor.system.common.properties.QiNiuProperties;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-25
 */
@Service
public class BackAttachmentService extends ServiceImpl<AttachmentMapper, Attachment> {

    @Autowired
    private QiNiuProperties qiNiuProperties;

    public MyPage<Attachment> myPage(PageQuery pageQuery, AttachmentQuery1 query) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<Attachment> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<Attachment> queryWrapper = new QueryWrapper<>();

        //      3调用Mapper
        IPage<Attachment> iPage = getBaseMapper().selectPage(page, queryWrapper);


//        将mybatis plus 分页对象转换成我们的
        MyPage<Attachment> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }


    public Attachment mySave(AttachmentBO1 bo) {

        Attachment po = BeanCopyUtils.copyBean(bo, Attachment.class);

        getBaseMapper().insert(po);

        return po;
    }

    public void myRemoveById(Long id) {
        getBaseMapper().deleteById(id);
    }

    public void myPatch(AttachmentBO1 bo) {

        Attachment updating = BeanCopyUtils.copyBean(bo,Attachment.class);

        getBaseMapper().updateById(updating);

    }


    @Transactional
    public Attachment myUpload(MultipartFile file) throws IOException {

        //获取文件原始全名
        String originalFilename = file.getOriginalFilename();
        //文件名前缀
        String prefix = FileUtils.getPrefix(originalFilename);
        //文件名后缀
        String suffix = FileUtils.getSuffix(originalFilename);
        //获取文件大小(字节)
        long sizeByte = file.getSize();

        //新随机文件名
        String randomPrefix = UUID.randomUUID().toString().replace( "-", "" );
        String newName = randomPrefix + "."+ suffix;

        //文件上传后返回的地址或路径
        String url = "";
        // 储存类型 1七牛 2本地
        Integer storeType = 1;

        if(storeType.compareTo(1)==0){

            String accessKey = qiNiuProperties.getAccessKey(); //密钥1
            String secretKey = qiNiuProperties.getSecretKey(); //密钥2
            String bucket = qiNiuProperties.getBucket(); //储存桶名
            String domain = qiNiuProperties.getDomain(); //七牛个人域名

            //构造一个配置类
            Configuration cfg = new Configuration();
            //上传管理器
            UploadManager uploadManager = new UploadManager(cfg);
            //回调内容配置 其他参数参考文档
            StringMap putPolicy = new StringMap();
            //putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
            //putPolicy.put("callbackBodyType", "application/json");
            // 授权过期时间
            long expireSeconds = 3600;

            //创建认证Token
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);

            //执行上传
            try {
                byte[] uploadBytes = file.getBytes();
                Response response = uploadManager.put(uploadBytes, newName, upToken);
                //解析上传成功的结果
                //String bodyString = response.bodyString();
                //DefaultPutRet putRet = new Gson().fromJson(bodyString, DefaultPutRet.class);
                url = domain + newName;
            } catch (QiniuException e) {
                e.printStackTrace();
                throw new BusinessException("七牛云配置错误");
            }
        } else if(storeType.compareTo(2)==0){

            // 这里只是试验上传到本地, 但并没有提供文件访问功能, 微服务这里推荐自建文件服务或使用云储存
            String path = "d://"; //储存到本地时的路径
            File pic = new File(path + newName);
            // 判断文件目录是否存在，不存在就创建文件目录
            if (!pic.getParentFile().exists()) {
                pic.getParentFile().mkdirs();// 创建父级文件路径
            }
            //保存文件
            file.transferTo(pic);
            url = path + newName;
        } else {
            throw new BusinessException("文件储存策略配置错误");
        }


        //保存信息到数据库
        Attachment saving = new Attachment();
        //saving.setUserId(id);
        saving.setName(newName);
        saving.setType(suffix);
        saving.setSize(sizeByte);
        saving.setStoreType(storeType);
        saving.setUrl(url);

        save(saving);
        return saving;
    }
}
