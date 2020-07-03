package com.juchia.tutor.upms.common.validate;

//        控制层手动使用校验器

//        hibernate的校验器：
//              Foo foo = new Foo();
//              foo.setAge(22);
//              foo.setEmail("000");
//              ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
//              Validator validator = vf.getValidator();
//
//              Set<ConstraintViolation<Foo>> set = validator.validate(foo);
//              for (ConstraintViolation<Foo> constraintViolation : set) {
//                  System.out.println(constraintViolation.getMessage());
//              }

//        spring boot 可以直接注入spring加强的校验器：
//              @Autowired
//              Validator globalValidator //注意是javax.validation.Validator包中的
//              Foo foo = new Foo();
//              foo.setAge(22);
//              foo.setEmail("000");
//
//              Set<ConstraintViolation<Foo>> set = globalValidator.validate(foo);
//              for (ConstraintViolation<Foo> constraintViolation : set) {
//                  System.out.println(constraintViolation.getMessage());
//              }