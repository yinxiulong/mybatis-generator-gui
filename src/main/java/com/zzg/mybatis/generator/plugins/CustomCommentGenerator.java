package com.zzg.mybatis.generator.plugins;


import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * 自定义Java模板注释 实现类
 *
 * @author Administrator
 */
public class CustomCommentGenerator implements CommentGenerator {

    /**
     * properties配置文件
     */
    private Properties properties;
    /**
     * properties配置文件
     */
    private Properties systemPro;

    /**
     * 父类时间
     */
    private boolean suppressDate;

    /**
     * 父类所有注释
     */
    private boolean suppressAllComments;

    /**
     * Java文件头注释
     */
    private boolean fileComments;

    private boolean isAnnotations;

    /**
     * 是否给 settr 添加注释
     */
    private boolean issetterAllComments;
    /**
     * 当前时间
     */
    private String currentDateStr;

    /**
     * @return
     * @throws
     * @Title addConfigurationProperties
     * @Description: 从该配置中的任何属性添加此实例的属性CommentGenerator配置。
     * 这个方法将在任何其他方法之前被调用。
     * @Author fendo
     * @Date 2017年10月5日 下午3:45:58
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        isAnnotations = isTrue(properties.getProperty("annotations"));
    }



    /**
     * 构造方法
     * 默认为 注释变量赋值： false 为添加注释  ture 为 不添加注释
     */
    public CustomCommentGenerator() {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        fileComments = true;
        issetterAllComments = true;
        currentDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    /**
     * Java类的类注释
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append(" ");
        sb.append(getDateString());
        innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
        innerClass.addJavaDocLine(" */");
    }

    /**
     * 为类添加注释
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);
        sb.append(" * @author ");
        sb.append(systemPro.getProperty("user.name"));
        sb.append(" ");
        sb.append(currentDateStr);
        innerClass.addJavaDocLine(" */");
    }


    /**
     * Mybatis的Mapper.xml文件里面的注释
     */
    @Override
    public void addComment(XmlElement xmlElement) {

    }
    /**
     * @return
     * @throws
     * @Title getDateString
     * @Description: 此方法返回格式化的日期字符串以包含在Javadoc标记中和XML注释。 如果您不想要日期，则可以返回null在这些文档元素中。
     * @Author fendo
     * @Date 2017年10月5日 下午3:45:58
     */
    protected String getDateString() {
        String result = null;
        if (!suppressDate) {
            result = currentDateStr;
        }
        return result;
    }

    /**
     * 此方法为其添加了自定义javadoc标签。
     *
     * @param javaElement 添加注释类
     * @param method      方法类 用于获取方法信息
     */
    protected void addJavadocTag(JavaElement javaElement, Method method) {

        javaElement.addJavaDocLine(" *");
        StringBuilder sb = new StringBuilder();
        sb.append(" * 功能说明：");
        // 转换方法名大写
        String methodName1 = method.getName().toUpperCase();
        // 添加指定方法的注释
        String key = null;
        try {
            key = CustomCommentMsg.valueOf(methodName1).getKey();
        } catch (IllegalArgumentException e) {
            sb.append(MergeConstants.NEW_ELEMENT_TAG);
        }
        if (methodName1.equalsIgnoreCase(key)) {
            sb.append(getValue(methodName1));
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        // 功能说明
        javaElement.addJavaDocLine(sb.toString());

        // 获取方法的 参数
        List<Parameter> parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            javaElement.addJavaDocLine(new StringBuilder().append(" * @param ").append(parameter.getName()).toString());
        }
        // 获取返回的类型
        FullyQualifiedJavaType returnType = method.getReturnType();
        // 判断返回的类型是否weinull
        if (returnType != null) {
            javaElement.addJavaDocLine(new StringBuilder().append(" * @return  ").append(returnType.getShortName()).toString());
        }
    }

    /**
     * 获取枚举信息
     *
     * @param methodName
     * @return
     */
    private String getValue(String methodName) {
        return CustomCommentMsg.valueOf(methodName).getValue();
    }


    /**
     * 为枚举添加注释
     */
    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerEnum.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString().replace("\n", " "));
        innerEnum.addJavaDocLine(" */");
    }

    /**
     * Java Entity 属性注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            StringBuilder sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedColumn.getRemarks());
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine(" */");
        }

        if (isAnnotations) {
            boolean isId = false;
            for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
                if (introspectedColumn == column) {
                    isId = true;
                    field.addAnnotation("@Id");
                    field.addAnnotation("@GeneratedValue");
                    break;
                }
            }
            if (!introspectedColumn.isNullable() && !isId) {
                field.addAnnotation("@NotEmpty");
            }
            if (introspectedColumn.isIdentity()) {
                if (introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement().equals("JDBC")) {
                    field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
                } else {
                    field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
                }
            } else if (introspectedColumn.isSequenceColumn()) {
                field.addAnnotation("@SequenceGenerator(name=\"\",sequenceName=\"" + introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement() + "\")");
            }
        }

    }

    /**
     * 为字段添加注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }


    /**
     * 普通方法的注释，这里主要是XXXMapper.java里面的接口方法的注释
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine("/**");
        addJavadocTag(method, method);
        method.addJavaDocLine(" */");
    }

    /**
     * 给Java文件加注释，这个注释是在文件的顶部，也就是package上面。
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        if (fileComments) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        compilationUnit.addFileCommentLine("/*");
        compilationUnit.addFileCommentLine("*");
        compilationUnit.addFileCommentLine("* " + compilationUnit.getType().getShortName() + ".java");
        compilationUnit.addFileCommentLine("* Copyright(C) 2018-2020 成都太阳高科");
        compilationUnit.addFileCommentLine("* @date " + sdf.format(new Date()) + "");
        compilationUnit.addFileCommentLine("*/");
    }

    /**
     * Java Entity 为模型类添加注释
     */
    @Override
    public void addModelClassComment(TopLevelClass arg0, IntrospectedTable introspectedTable) {
        arg0.addJavaDocLine("/**");
        arg0.addJavaDocLine(" * @author ");
        if (suppressAllComments) {
            arg0.addAnnotation("@Table(name=\"" + introspectedTable.getFullyQualifiedTableNameAtRuntime() + "\")");
        }
        arg0.addJavaDocLine(" */");

    }

    /**
     * 为调用此方法作为根元素的第一个子节点添加注释。
     */
    @Override
    public void addRootComment(XmlElement arg0) {

    }


    /**
     * 给setter方法加注释
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (issetterAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        method.addJavaDocLine(" */");
    }

    /**
     * 给getter方法加注释
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (issetterAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getActualColumnName());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        method.addJavaDocLine(" */");
    }
}
