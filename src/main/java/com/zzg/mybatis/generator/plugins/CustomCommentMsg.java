package com.zzg.mybatis.generator.plugins;

/**
 * 自定义 Java类的注册方法
 *
 * @author YINXIULONG
 */
public enum CustomCommentMsg {
    /**
     * mapper 注释
     */

    INSERT("INSERT", "往数据库插入一条记录"),
    INSERTSELECTIVE("INSERTSELECTIVE", "根据数据库字段判断是否为null选择性插入数据"),
    DELETEBYPRIMARYKEY("DELETEBYPRIMARYKEY", "根据数据库主键ID删除一条记录"),
    UPDATEBYPRIMARYKEY("UPDATEBYPRIMARYKEY", "根据数据库主键ID修改一条记录"),
    UPDATEBYPRIMARYKEYSELECTIVE("UPDATEBYPRIMARYKEYSELECTIVE", "根据数据库主键ID和数据库字段选择性修改一条记录"),
    SELECTBYPRIMARYKEY("SELECTBYPRIMARYKEY", "根据数据库主键ID查询一条数据"),
    /**
     * example 动态条件
     */

    COUNTBYEXAMPLE("COUNTBYEXAMPLE", "根据自定义条件统计数据"),
    SELECTBYEXAMPLE("SELECTBYEXAMPLE", "根据自定义条件查询数据"),
    UPDATEBYEXAMPLESELECTIVE("UPDATEBYEXAMPLESELECTIVE", "根据自定义条件和字段修改数据"),
    UPDATEBYEXAMPLE("UPDATEBYEXAMPLE", "根据自定义条件修改数据"),
    DELETEBYEXAMPLE("DELETEBYEXAMPLE", "根据自定义条件删除数据");

    private String key;
    private String value;

    CustomCommentMsg(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return value
     */
    public String getValue() {
        return value;

    }
}
