package cn.poem.solon.admin.gen.utils;


import cn.poem.solon.admin.gen.config.GenConfig;
import cn.poem.solon.admin.gen.constant.GenConstants;
import cn.poem.solon.admin.gen.domain.entity.GenTable;
import cn.poem.solon.admin.gen.domain.entity.GenTableColumn;
import cn.poem.solon.admin.gen.enums.HtmlType;
import cn.poem.solon.admin.gen.enums.QueryType;
import cn.poem.solon.admin.gen.enums.YesOrNo;
import com.mybatisflex.core.util.StringUtil;
import org.noear.solon.Solon;
import org.noear.solon.validation.util.StringUtils;

import java.util.Arrays;

/**
 * 代码生成器 工具类
 * 
 * @author ruoyi
 */
public class GenUtils
{
    /** 代码生成配置 */
    private static GenConfig genConfig;

    /** 空字符串 */
    private static final String NULLSTR = "";

    /** 下划线 */
    private static final char SEPARATOR = '_';

    static {
        Solon.context().getBeanAsync(GenConfig.class,item->genConfig=item);
    }

    /**
     * 初始化表信息
     */
    public static void initTable(GenTable genTable, Long crateUser)
    {
        genTable.setClassName(convertClassName(genTable.getTableName()));
        genTable.setPackageName(GenConfig.getPackageName());
        genTable.setModuleName(getModuleName(GenConfig.getPackageName()));
        genTable.setBusinessName(getBusinessName(genTable.getTableName()));
        genTable.setFunctionName(genTable.getTableComment());
        genTable.setFunctionAuthor(GenConfig.getAuthor());
        genTable.setCreateUser(crateUser);
    }

    /**
     * 初始化列属性字段
     */
    public static void initColumnField(GenTableColumn column, GenTable table)
    {
        String dataType = column.getColumnType();
        String columnName = column.getColumnName();
        column.setTableId(table.getTableId());
        column.setCreateUser(table.getCreateUser());
        // 设置java字段名
        column.setJavaField(toCamelCase(columnName));
        // 设置默认类型
        column.setJavaType(GenConstants.TYPE_STRING);
        column.setQueryType(QueryType.EQ);

        if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType))
        {
            column.setHtmlType(HtmlType.INPUT);
        }
        else if(arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType)){
            column.setHtmlType(HtmlType.TEXTAREA);
        }
        else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType))
        {
            column.setJavaType(GenConstants.TYPE_DATE);
            column.setHtmlType(HtmlType.DATE);
        }
        else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType))
        {
            column.setHtmlType(HtmlType.INPUT);

            // 如果是浮点型 统一用BigDecimal
            String str = column.getColumnType();
            // 如果是整形
            if (str != null && str.length() <= 10)
            {
                column.setJavaType(GenConstants.TYPE_INTEGER);
            }
            // 长整形
            else
            {
                column.setJavaType(GenConstants.TYPE_LONG);
            }
        }

        // 插入字段（默认所有字段都需要插入）
        column.setInsert(YesOrNo.YES);

        // 编辑字段
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_EDIT, columnName) && !column.isPk())
        {
            column.setEdit(YesOrNo.YES);
        }
        // 列表字段
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName) && !column.isPk())
        {
            column.setList(YesOrNo.YES);
        }
        // 查询字段
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPk())
        {
            column.setQuery(YesOrNo.YES);
        }

        // 查询字段类型
        if ("name".equals(columnName))
        {
            column.setQueryType(QueryType.LIKE);
        }
        // 状态字段设置单选框
        if ("status".equals(columnName))
        {
            column.setHtmlType(HtmlType.RADIO);
        }
        // 类型&性别字段设置下拉框
        else if ("type".equals(columnName)||"sex".equals(columnName)
                )
        {
            column.setHtmlType(HtmlType.SELECT);
        }
        // 图片字段设置图片上传控件
        else if ("image".equals(columnName))
        {
            column.setHtmlType(HtmlType.IMAGE_UPLOAD);
        }
        // 文件字段设置文件上传控件
        else if ("file".equals(columnName))
        {
            column.setHtmlType(HtmlType.IMAGE_UPLOAD);
        }
        // 内容字段设置富文本控件
        else if ("content".equals(columnName))
        {
            column.setHtmlType(HtmlType.EDITOR);
        }
    }

    /**
     * 校验数组是否包含指定值
     * 
     * @param arr 数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue)
    {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 获取模块名
     * 
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName)
    {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        return packageName.substring(lastIndex + 1, nameLength);
    }

    /**
     * 获取业务名
     * 
     * @param tableName 表名
     * @return 业务名
     */
    public static String getBusinessName(String tableName)
    {
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        return  tableName.substring(lastIndex + 1, nameLength);
    }

    /**
     * 表名转换成Java类名
     * 
     * @param tableName 表名称
     * @return 类名
     */
    public static String convertClassName(String tableName)
    {
        boolean autoRemovePre = GenConfig.getAutoRemovePre();
        String tablePrefix = GenConfig.getTablePrefix();
        if (autoRemovePre && tablePrefix!=null && !tablePrefix.isEmpty())
        {
            String[] searchList = tablePrefix.split(",");
            tableName = replaceFirst(tableName, searchList);
        }
        return toCamelCase(tableName);
    }

    /**
     * 批量替换前缀
     * 
     * @param replacementm 替换值
     * @param searchList 替换列表
     * @return
     */
    public static String replaceFirst(String replacementm, String[] searchList)
    {
        String text = replacementm;
        for (String searchString : searchList)
        {
            if (replacementm.startsWith(searchString))
            {
                text = replacementm.replaceFirst(searchString, "");
                break;
            }
        }
        return text;
    }


    /**
     * 驼峰式命名法
     * 例如：user_name->userName
     */
    private static String toCamelCase(String s)
    {
        if (s == null)
        {
            return null;
        }
        if (s.indexOf(SEPARATOR) == -1)
        {
            return s;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            if (c == SEPARATOR)
            {
                upperCase = true;
            }
            else if (upperCase)
            {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            }
            else
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
