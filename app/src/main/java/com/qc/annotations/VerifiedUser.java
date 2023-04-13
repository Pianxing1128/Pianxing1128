package com.qc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifiedUser {

}


/**
 * 四大元注解：
 *
 * @Target
 *
 * ElementType.type    放在类  接口   枚举中
 * ElementType.field     声明参数 上面 也就是属性
 * ElementType.method    方法中
 * ElementType.parameter   方法参数
 * ElementType.constrouct   构造方法
 * ElementType.Local_variable  局部变量
 * ElementType.annotation_type
 * ElementType.package 作用在包上面
 * ElementType.Type_parameter      1.8 特有的 类型参数声明
 * ElementType.TYPE_USE   1.8 特有的  类型的使用
 * @retation
 *
 * resouce    保留在源码阶段       as做代码检测  (例如 @indef   @drawableres)   apt 技术
 * class  保留在字节码阶段          在插桩  字节码修改操作
 * runtime  保留在运行时             java虚拟机运行 中  主要配合注解 做反射操作
 * @Documented
 *
 * 拥有此类注解  可以被javadoc 工具文档化   它代表着此注解会被javadoc工具提取成文档。在doc文档中的内容会因为此注解的信息内容不同而不同。
 *
 * @@Inherited
 *
 * 允许子类继承父类的注解    即拥有此注解的元素其子类可以继承父类的注解。

 */