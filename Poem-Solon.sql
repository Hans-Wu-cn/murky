--
-- PostgreSQL database dump
--

-- Dumped from database version 12.16 (Debian 12.16-1.pgdg120+1)
-- Dumped by pg_dump version 14.2

-- Started on 2023-11-05 16:26:42

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE "poem-solon";
--
-- TOC entry 3113 (class 1262 OID 16515)
-- Name: poem-solon; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE "poem-solon" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


\connect -reuse-previous=on "dbname='poem-solon'"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 8 (class 2615 OID 16516)
-- Name: demo; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA demo;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 203 (class 1259 OID 16517)
-- Name: gen_table; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.gen_table (
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    create_user bigint,
    update_user bigint,
    table_id bigint NOT NULL,
    table_name character varying,
    table_comment character varying,
    sub_table_name character varying,
    sub_table_fk_name character varying,
    class_name character varying,
    tpl_category smallint DEFAULT 0 NOT NULL,
    package_name character varying,
    module_name character varying,
    business_name character varying,
    function_name character varying,
    function_author character varying,
    gen_type character varying DEFAULT 0 NOT NULL,
    gen_path character varying DEFAULT '/'::character varying NOT NULL,
    options character varying,
    remark character varying
);


--
-- TOC entry 3114 (class 0 OID 0)
-- Dependencies: 203
-- Name: TABLE gen_table; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON TABLE demo.gen_table IS '代码生成业务表';


--
-- TOC entry 3115 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.create_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.create_time IS '创建时间';


--
-- TOC entry 3116 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.update_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.update_time IS '修改时间';


--
-- TOC entry 3117 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.create_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.create_user IS '创建人';


--
-- TOC entry 3118 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.update_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.update_user IS '修改人';


--
-- TOC entry 3119 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.table_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.table_id IS '主键';


--
-- TOC entry 3120 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.table_name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.table_name IS '表名称';


--
-- TOC entry 3121 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.table_comment; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.table_comment IS '表描述';


--
-- TOC entry 3122 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.sub_table_name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.sub_table_name IS '关联子表的表名';


--
-- TOC entry 3123 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.sub_table_fk_name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.sub_table_fk_name IS '子表关联的外键名';


--
-- TOC entry 3124 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.class_name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.class_name IS '实体类名称';


--
-- TOC entry 3125 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.tpl_category; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.tpl_category IS '使用的模板（0:单表操作 1:树表操作）';


--
-- TOC entry 3126 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.package_name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.package_name IS '生成包路径';


--
-- TOC entry 3127 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.module_name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.module_name IS '生成模块名';


--
-- TOC entry 3128 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.business_name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.business_name IS '生成业务名';


--
-- TOC entry 3129 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.function_name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.function_name IS '生成功能名';


--
-- TOC entry 3130 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.function_author; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.function_author IS '生成功能作者';


--
-- TOC entry 3131 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.gen_type; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.gen_type IS '生成代码方式（0zip压缩包 1自定义路径）';


--
-- TOC entry 3132 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.gen_path; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.gen_path IS '生成路径（不填默认项目路径）';


--
-- TOC entry 3133 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.options; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.options IS '其它生成选项';


--
-- TOC entry 3134 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN gen_table.remark; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table.remark IS '备注';


--
-- TOC entry 204 (class 1259 OID 16526)
-- Name: gen_table_column; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.gen_table_column (
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    create_user bigint,
    update_user bigint,
    column_id bigint NOT NULL,
    table_id character varying,
    column_name character varying NOT NULL,
    column_comment character varying,
    column_type character varying,
    java_type character varying,
    java_field character varying,
    pk smallint DEFAULT 0 NOT NULL,
    increment smallint DEFAULT 0 NOT NULL,
    required smallint DEFAULT 0 NOT NULL,
    insert smallint DEFAULT 0 NOT NULL,
    edit smallint DEFAULT 0 NOT NULL,
    list smallint DEFAULT 0 NOT NULL,
    query smallint DEFAULT 0 NOT NULL,
    query_type smallint DEFAULT 0 NOT NULL,
    html_type smallint DEFAULT 0 NOT NULL,
    dict_type character varying,
    sort smallint
);


--
-- TOC entry 3135 (class 0 OID 0)
-- Dependencies: 204
-- Name: TABLE gen_table_column; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON TABLE demo.gen_table_column IS '代码生成业务表字段';


--
-- TOC entry 3136 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.create_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.create_time IS '创建时间';


--
-- TOC entry 3137 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.update_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.update_time IS '修改时间';


--
-- TOC entry 3138 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.create_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.create_user IS '创建人';


--
-- TOC entry 3139 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.update_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.update_user IS '修改人';


--
-- TOC entry 3140 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.column_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.column_id IS '主键';


--
-- TOC entry 3141 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.table_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.table_id IS '归属表编号';


--
-- TOC entry 3142 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.column_name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.column_name IS '列名称';


--
-- TOC entry 3143 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.column_comment; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.column_comment IS '列描述';


--
-- TOC entry 3144 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.column_type; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.column_type IS '列类型';


--
-- TOC entry 3145 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.java_type; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.java_type IS 'JAVA类型';


--
-- TOC entry 3146 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.java_field; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.java_field IS 'JAVA字段名';


--
-- TOC entry 3147 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.pk; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.pk IS '是否主键（0:否 1是）';


--
-- TOC entry 3148 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.increment; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.increment IS '是否自增（0:否 1:是）';


--
-- TOC entry 3149 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.required; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.required IS '是否必填（0:否 1:是）';


--
-- TOC entry 3150 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.insert; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.insert IS '是否为插入字段（0:否 1:是）';


--
-- TOC entry 3151 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.edit; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.edit IS '是否编辑字段（0:否 1:是）';


--
-- TOC entry 3152 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.list; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.list IS '是否编辑字段（0:否  1:是）';


--
-- TOC entry 3153 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.query; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.query IS '是否查询字段（0:否 1是）';


--
-- TOC entry 3154 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.query_type; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.query_type IS '查询方式（0:等于、1:不等于、2:大于、3:小于、4:范围 5:模糊搜索）';


--
-- TOC entry 3155 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.html_type; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.html_type IS '显示类型（0:文本框、1:文本域、2:下拉框、3:复选框、4:单选框、5:日期控件 6:图片上传控件 7:富文本）';


--
-- TOC entry 3156 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.dict_type; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.dict_type IS '字典类型';


--
-- TOC entry 3157 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN gen_table_column.sort; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.gen_table_column.sort IS '排序';


--
-- TOC entry 205 (class 1259 OID 16541)
-- Name: poem_dept; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.poem_dept (
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    create_user bigint,
    update_user bigint,
    dept_id bigint NOT NULL,
    dept_name character varying NOT NULL,
    parent_dept bigint DEFAULT 0 NOT NULL,
    sort smallint DEFAULT 0 NOT NULL
);


--
-- TOC entry 3158 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_dept.create_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dept.create_time IS '创建时间';


--
-- TOC entry 3159 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_dept.update_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dept.update_time IS '修改时间';


--
-- TOC entry 3160 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_dept.create_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dept.create_user IS '创建人';


--
-- TOC entry 3161 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_dept.update_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dept.update_user IS '修改人';


--
-- TOC entry 3162 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_dept.dept_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dept.dept_id IS '主键';


--
-- TOC entry 3163 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_dept.dept_name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dept.dept_name IS '部门名称';


--
-- TOC entry 3164 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_dept.parent_dept; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dept.parent_dept IS '上级部门';


--
-- TOC entry 3165 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_dept.sort; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dept.sort IS '排序';


--
-- TOC entry 206 (class 1259 OID 16549)
-- Name: poem_dept_ancestors; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.poem_dept_ancestors (
    dept_id bigint NOT NULL,
    ancestors bigint NOT NULL
);


--
-- TOC entry 3166 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN poem_dept_ancestors.dept_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dept_ancestors.dept_id IS '部门id';


--
-- TOC entry 3167 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN poem_dept_ancestors.ancestors; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dept_ancestors.ancestors IS '部门id的上级id和祖级id';


--
-- TOC entry 207 (class 1259 OID 16552)
-- Name: poem_dict_data; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.poem_dict_data (
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    create_user bigint,
    update_user bigint,
    dict_code bigint NOT NULL,
    dict_sort smallint DEFAULT 0 NOT NULL,
    dict_label character varying NOT NULL,
    dict_value character varying NOT NULL,
    dict_type character varying NOT NULL,
    status smallint DEFAULT 0 NOT NULL,
    remark character varying
);


--
-- TOC entry 3168 (class 0 OID 0)
-- Dependencies: 207
-- Name: TABLE poem_dict_data; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON TABLE demo.poem_dict_data IS '字典数据表';


--
-- TOC entry 3169 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN poem_dict_data.create_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_data.create_time IS '创建时间';


--
-- TOC entry 3170 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN poem_dict_data.update_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_data.update_time IS '修改时间';


--
-- TOC entry 3171 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN poem_dict_data.create_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_data.create_user IS '创建人';


--
-- TOC entry 3172 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN poem_dict_data.update_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_data.update_user IS '修改人';


--
-- TOC entry 3173 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN poem_dict_data.dict_code; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_data.dict_code IS '字典编码';


--
-- TOC entry 3174 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN poem_dict_data.dict_sort; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_data.dict_sort IS '字典排序';


--
-- TOC entry 3175 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN poem_dict_data.dict_label; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_data.dict_label IS '字典标签';


--
-- TOC entry 3176 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN poem_dict_data.dict_value; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_data.dict_value IS '字典值';


--
-- TOC entry 3177 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN poem_dict_data.dict_type; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_data.dict_type IS '字典类型';


--
-- TOC entry 3178 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN poem_dict_data.status; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_data.status IS '字典状态(0:正常 1:停用)';


--
-- TOC entry 3179 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN poem_dict_data.remark; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_data.remark IS '备注';


--
-- TOC entry 208 (class 1259 OID 16560)
-- Name: poem_dict_type; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.poem_dict_type (
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    create_user bigint,
    update_user bigint,
    dict_type_id bigint NOT NULL,
    dict_name character varying NOT NULL,
    dict_type character varying NOT NULL,
    status smallint DEFAULT 0 NOT NULL,
    remark character varying
);


--
-- TOC entry 3180 (class 0 OID 0)
-- Dependencies: 208
-- Name: TABLE poem_dict_type; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON TABLE demo.poem_dict_type IS '字典类型';


--
-- TOC entry 3181 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN poem_dict_type.create_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_type.create_time IS '创建时间';


--
-- TOC entry 3182 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN poem_dict_type.update_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_type.update_time IS '修改时间';


--
-- TOC entry 3183 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN poem_dict_type.create_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_type.create_user IS '创建人';


--
-- TOC entry 3184 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN poem_dict_type.update_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_type.update_user IS '修改人';


--
-- TOC entry 3185 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN poem_dict_type.dict_name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_type.dict_name IS '字典名称';


--
-- TOC entry 3186 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN poem_dict_type.dict_type; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_type.dict_type IS '字典类型';


--
-- TOC entry 3187 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN poem_dict_type.status; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_type.status IS '字典状态 0:正常 1:停用';


--
-- TOC entry 3188 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN poem_dict_type.remark; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_dict_type.remark IS '备注';


--
-- TOC entry 209 (class 1259 OID 16567)
-- Name: poem_i18n; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.poem_i18n (
    id bigint NOT NULL,
    i18n_key character varying NOT NULL,
    i18n_value character varying NOT NULL,
    language character varying NOT NULL,
    i18n_tag character varying NOT NULL,
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    create_user bigint,
    update_user bigint
);


--
-- TOC entry 3189 (class 0 OID 0)
-- Dependencies: 209
-- Name: TABLE poem_i18n; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON TABLE demo.poem_i18n IS 'i18n数据管理';


--
-- TOC entry 3190 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN poem_i18n.id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_i18n.id IS '主键';


--
-- TOC entry 3191 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN poem_i18n.i18n_key; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_i18n.i18n_key IS 'i18n编码';


--
-- TOC entry 3192 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN poem_i18n.i18n_value; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_i18n.i18n_value IS 'i18n值';


--
-- TOC entry 3193 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN poem_i18n.language; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_i18n.language IS 'i18n地区编码(字典:i18n)';


--
-- TOC entry 3194 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN poem_i18n.i18n_tag; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_i18n.i18n_tag IS 'i18n标签(字典:i18n:tag)';


--
-- TOC entry 3195 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN poem_i18n.create_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_i18n.create_time IS '创建时间';


--
-- TOC entry 3196 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN poem_i18n.update_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_i18n.update_time IS '修改时间';


--
-- TOC entry 3197 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN poem_i18n.create_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_i18n.create_user IS '创建人';


--
-- TOC entry 3198 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN poem_i18n.update_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_i18n.update_user IS '修改人';


--
-- TOC entry 210 (class 1259 OID 16573)
-- Name: poem_menu; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.poem_menu (
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    create_user bigint,
    update_user bigint,
    menu_id bigint NOT NULL,
    label character varying NOT NULL,
    name character varying,
    path character varying,
    open_type smallint DEFAULT 1 NOT NULL,
    auth character varying,
    parent_menu_id bigint DEFAULT 0,
    type smallint,
    sort smallint DEFAULT 0,
    component character varying,
    icon character varying,
    is_cache smallint DEFAULT 0 NOT NULL,
    is_display smallint DEFAULT 0 NOT NULL,
    is_outside smallint DEFAULT 0 NOT NULL,
    query character varying
);


--
-- TOC entry 3199 (class 0 OID 0)
-- Dependencies: 210
-- Name: TABLE poem_menu; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON TABLE demo.poem_menu IS '菜单/权限表';


--
-- TOC entry 3200 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.create_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.create_time IS '创建时间';


--
-- TOC entry 3201 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.update_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.update_time IS '修改时间';


--
-- TOC entry 3202 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.create_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.create_user IS '创建人';


--
-- TOC entry 3203 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.update_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.update_user IS '修改人';


--
-- TOC entry 3204 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.label; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.label IS '菜单标题';


--
-- TOC entry 3205 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.name IS '菜单名称';


--
-- TOC entry 3206 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.path; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.path IS '路由地址';


--
-- TOC entry 3207 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.open_type; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.open_type IS '打开方式 1:当前窗口 2:新窗口';


--
-- TOC entry 3208 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.auth; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.auth IS '菜单权限码';


--
-- TOC entry 3209 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.parent_menu_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.parent_menu_id IS '上级菜单id';


--
-- TOC entry 3210 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.type; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.type IS '菜单类型  0:目录 1:侧边菜单 2:按钮';


--
-- TOC entry 3211 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.sort; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.sort IS '排序';


--
-- TOC entry 3212 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.component; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.component IS '组件路径';


--
-- TOC entry 3213 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.icon; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.icon IS '目录图标';


--
-- TOC entry 3214 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.is_cache; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.is_cache IS '是否开启缓存 0:关闭  1:开启';


--
-- TOC entry 3215 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.is_display; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.is_display IS '是否显示在菜单  0:显示  1:隐藏';


--
-- TOC entry 3216 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.is_outside; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.is_outside IS '是否外链  0:否  1:是';


--
-- TOC entry 3217 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN poem_menu.query; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_menu.query IS '路由参数';


--
-- TOC entry 211 (class 1259 OID 16585)
-- Name: poem_role; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.poem_role (
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    create_user bigint,
    update_user bigint,
    role_id bigint NOT NULL,
    role_name character varying NOT NULL,
    describe character varying,
    role_code character varying NOT NULL,
    data_scope smallint DEFAULT 0 NOT NULL,
    dept_id bigint
);


--
-- TOC entry 3218 (class 0 OID 0)
-- Dependencies: 211
-- Name: TABLE poem_role; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON TABLE demo.poem_role IS '角色表';


--
-- TOC entry 3219 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN poem_role.create_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role.create_time IS '创建时间';


--
-- TOC entry 3220 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN poem_role.update_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role.update_time IS '修改时间';


--
-- TOC entry 3221 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN poem_role.create_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role.create_user IS '创建人';


--
-- TOC entry 3222 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN poem_role.update_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role.update_user IS '修改人';


--
-- TOC entry 3223 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN poem_role.role_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role.role_id IS '角色id';


--
-- TOC entry 3224 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN poem_role.role_name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role.role_name IS '角色名';


--
-- TOC entry 3225 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN poem_role.describe; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role.describe IS '描述';


--
-- TOC entry 3226 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN poem_role.role_code; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role.role_code IS '角色标识';


--
-- TOC entry 3227 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN poem_role.data_scope; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role.data_scope IS '数据范围(0:全部数据权限 1:自定数据权限 2:本部门及以下数据权限 3:本部门数据权限 4:仅本人 )';


--
-- TOC entry 3228 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN poem_role.dept_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role.dept_id IS '所属部门';


--
-- TOC entry 212 (class 1259 OID 16592)
-- Name: poem_role_dept; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.poem_role_dept (
    role_id bigint NOT NULL,
    dept_id bigint NOT NULL
);


--
-- TOC entry 3229 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN poem_role_dept.role_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role_dept.role_id IS '角色id';


--
-- TOC entry 3230 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN poem_role_dept.dept_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role_dept.dept_id IS '部门id';


--
-- TOC entry 213 (class 1259 OID 16595)
-- Name: poem_role_menu; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.poem_role_menu (
    menu_id bigint NOT NULL,
    role_id bigint NOT NULL
);


--
-- TOC entry 3231 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN poem_role_menu.menu_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role_menu.menu_id IS '菜单id';


--
-- TOC entry 3232 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN poem_role_menu.role_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_role_menu.role_id IS '角色id';


--
-- TOC entry 214 (class 1259 OID 16598)
-- Name: poem_user; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.poem_user (
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    create_user bigint,
    update_user bigint,
    user_id bigint NOT NULL,
    user_name character varying NOT NULL,
    account character varying NOT NULL,
    password character varying NOT NULL,
    sex smallint NOT NULL,
    email character varying,
    dept_id bigint,
    language character varying,
    salt character varying
);


--
-- TOC entry 3233 (class 0 OID 0)
-- Dependencies: 214
-- Name: TABLE poem_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON TABLE demo.poem_user IS '用户表';


--
-- TOC entry 3234 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN poem_user.create_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user.create_time IS '创建时间';


--
-- TOC entry 3235 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN poem_user.update_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user.update_time IS '修改时间';


--
-- TOC entry 3236 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN poem_user.create_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user.create_user IS '创建人';


--
-- TOC entry 3237 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN poem_user.update_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user.update_user IS '修改人';


--
-- TOC entry 3238 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN poem_user.user_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user.user_id IS '用户id';


--
-- TOC entry 3239 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN poem_user.user_name; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user.user_name IS '用户名';


--
-- TOC entry 3240 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN poem_user.account; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user.account IS '账号';


--
-- TOC entry 3241 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN poem_user.password; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user.password IS '密码';


--
-- TOC entry 3242 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN poem_user.sex; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user.sex IS '性别 0:男性 1:女性 2:其他';


--
-- TOC entry 3243 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN poem_user.email; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user.email IS '邮箱';


--
-- TOC entry 3244 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN poem_user.dept_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user.dept_id IS '部门id';


--
-- TOC entry 3245 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN poem_user.language; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user.language IS '语言';


--
-- TOC entry 3246 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN poem_user.salt; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user.salt IS '密码加密盐值';


--
-- TOC entry 215 (class 1259 OID 16604)
-- Name: poem_user_role; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.poem_user_role (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


--
-- TOC entry 3247 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN poem_user_role.user_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user_role.user_id IS '用户id';


--
-- TOC entry 3248 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN poem_user_role.role_id; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.poem_user_role.role_id IS '角色id';


--
-- TOC entry 216 (class 1259 OID 16607)
-- Name: system_parameter; Type: TABLE; Schema: demo; Owner: -
--

CREATE TABLE demo.system_parameter (
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    create_user bigint,
    update_user bigint,
    id bigint NOT NULL,
    key character varying NOT NULL,
    value character varying NOT NULL,
    describe character varying
);


--
-- TOC entry 3249 (class 0 OID 0)
-- Dependencies: 216
-- Name: TABLE system_parameter; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON TABLE demo.system_parameter IS '系统参数配置';


--
-- TOC entry 3250 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN system_parameter.create_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.system_parameter.create_time IS '创建时间';


--
-- TOC entry 3251 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN system_parameter.update_time; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.system_parameter.update_time IS '修改时间';


--
-- TOC entry 3252 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN system_parameter.create_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.system_parameter.create_user IS '创建人';


--
-- TOC entry 3253 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN system_parameter.update_user; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.system_parameter.update_user IS '修改人';


--
-- TOC entry 3254 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN system_parameter.key; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.system_parameter.key IS '参数key';


--
-- TOC entry 3255 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN system_parameter.value; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.system_parameter.value IS '参数值';


--
-- TOC entry 3256 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN system_parameter.describe; Type: COMMENT; Schema: demo; Owner: -
--

COMMENT ON COLUMN demo.system_parameter.describe IS '描述';


--
-- TOC entry 3094 (class 0 OID 16517)
-- Dependencies: 203
-- Data for Name: gen_table; Type: TABLE DATA; Schema: demo; Owner: -
--



--
-- TOC entry 3095 (class 0 OID 16526)
-- Dependencies: 204
-- Data for Name: gen_table_column; Type: TABLE DATA; Schema: demo; Owner: -
--



--
-- TOC entry 3096 (class 0 OID 16541)
-- Dependencies: 205
-- Data for Name: poem_dept; Type: TABLE DATA; Schema: demo; Owner: -
--

INSERT INTO demo.poem_dept VALUES ('2023-09-11 15:02:07.115715', NULL, 43511453924630528, NULL, 58737314673807360, '集团名称', 56925072011030528, 0);
INSERT INTO demo.poem_dept VALUES ('2023-09-06 15:00:54.796371', '2023-10-20 11:44:17.03232', 43511453924630528, 43511453924630528, 56925072011030528, 'solon科技集团', 0, 0);
INSERT INTO demo.poem_dept VALUES ('2023-09-19 05:59:46.481439', '2023-10-20 11:44:17.109432', 61478072612352000, 43511453924630528, 61499932322398208, 'demo1创建的部门', 0, 1);


--
-- TOC entry 3097 (class 0 OID 16549)
-- Dependencies: 206
-- Data for Name: poem_dept_ancestors; Type: TABLE DATA; Schema: demo; Owner: -
--

INSERT INTO demo.poem_dept_ancestors VALUES (56925072011030528, 0);
INSERT INTO demo.poem_dept_ancestors VALUES (58737314673807360, 56925072011030528);
INSERT INTO demo.poem_dept_ancestors VALUES (58737314673807360, 0);
INSERT INTO demo.poem_dept_ancestors VALUES (61499932322398208, 0);


--
-- TOC entry 3098 (class 0 OID 16552)
-- Dependencies: 207
-- Data for Name: poem_dict_data; Type: TABLE DATA; Schema: demo; Owner: -
--

INSERT INTO demo.poem_dict_data VALUES ('2023-10-19 11:35:57.86173', NULL, 43511453924630528, NULL, 72456173201014784, 0, '管理端', 'admin', 'i18n:tag', 0, '管理端标签');
INSERT INTO demo.poem_dict_data VALUES ('2023-10-19 11:30:40.667188', '2023-10-19 11:32:10.586785', 43511453924630528, 43511453924630528, 72454842784563200, 1, '中国大陆', 'zh-CN', 'i18n:language', 0, NULL);
INSERT INTO demo.poem_dict_data VALUES ('2023-10-19 11:32:05.684663', '2023-10-19 18:32:34.179402', 43511453924630528, 43511453924630528, 72455199380094976, 0, '英国', 'en', 'i18n:language', 0, '默认,英语国家,排序第一的为默认语言,一般来说需要国际化语言的英语都应作为默认语言');
INSERT INTO demo.poem_dict_data VALUES ('2023-10-16 15:08:28.174', '2023-11-02 10:10:11.909748', 43511453924630528, 43511453924630528, 72262584047181824, 0, 'd4', '4', 'dd1', 0, 'd4');
INSERT INTO demo.poem_dict_data VALUES ('2023-10-16 15:08:28.174', '2023-11-02 10:10:11.910747', 43511453924630528, 43511453924630528, 72199365635047424, 0, 'd0', '0', 'dd1', 0, '测试数据');
INSERT INTO demo.poem_dict_data VALUES ('2023-10-16 15:08:28.174', '2023-11-02 10:10:11.91275', 43511453924630528, 43511453924630528, 72199436632031232, 1, 'd1', '1', 'dd1', 0, '测试数据');
INSERT INTO demo.poem_dict_data VALUES ('2023-10-16 15:08:28.174', '2023-11-02 10:13:55.915874', 43511453924630528, 43511453924630528, 72262422537117696, 0, 'd33', '3', 'dd1', 0, 'd3');


--
-- TOC entry 3099 (class 0 OID 16560)
-- Dependencies: 208
-- Data for Name: poem_dict_type; Type: TABLE DATA; Schema: demo; Owner: -
--

INSERT INTO demo.poem_dict_type VALUES ('2023-10-19 11:33:40.479917', NULL, 43511453924630528, NULL, 72455596979142656, '国际化标签', 'i18n:tag', 0, '');
INSERT INTO demo.poem_dict_type VALUES ('2023-10-19 11:21:22.964129', NULL, 43511453924630528, NULL, 72452503608655872, 'i18n地区编码', 'i18n:language', 0, '维护国际化编码');
INSERT INTO demo.poem_dict_type VALUES ('2023-10-16 15:08:28.174624', '2023-11-02 10:10:11.890746', 43511453924630528, 43511453924630528, 71422488293068800, 'dd', 'dd1', 0, '测试数据');


--
-- TOC entry 3100 (class 0 OID 16567)
-- Dependencies: 209
-- Data for Name: poem_i18n; Type: TABLE DATA; Schema: demo; Owner: -
--

INSERT INTO demo.poem_i18n VALUES (75884958822924288, 'd', 'd', 'en', 'admin', '2023-10-28 22:40:44.059075', NULL, 43511453924630528, NULL);
INSERT INTO demo.poem_i18n VALUES (75884958869061632, 'd', 'd', 'zh-CN', 'admin', '2023-10-28 22:40:44.06807', NULL, 43511453924630528, NULL);
INSERT INTO demo.poem_i18n VALUES (75903852686049280, 'a', 'a', 'en', 'admin', '2023-10-28 23:55:48.705133', NULL, 43511453924630528, NULL);
INSERT INTO demo.poem_i18n VALUES (75903852715409408, 'a', 'a', 'zh-CN', 'admin', '2023-10-28 23:55:48.71278', NULL, 43511453924630528, NULL);
INSERT INTO demo.poem_i18n VALUES (75929393027813376, 'sddddsssdasd', 'dddd', 'zh-CN', 'admin', '2023-10-29 01:37:17.999084', '2023-10-29 16:21:25.031125', 43511453924630528, 43511453924630528);
INSERT INTO demo.poem_i18n VALUES (76144367297867776, 'sddddsssdasd', '333d', 'en', 'admin', '2023-10-29 15:51:31.858316', '2023-10-29 16:21:25.034125', 43511453924630528, 43511453924630528);
INSERT INTO demo.poem_i18n VALUES (77531694559997952, 'button.submit', 'submit', 'en', 'admin', '2023-11-02 11:44:16.453536', NULL, 43511453924630528, NULL);
INSERT INTO demo.poem_i18n VALUES (77531694681632768, 'button.submit', '提 交', 'zh-CN', 'admin', '2023-11-02 11:44:16.481862', NULL, 43511453924630528, NULL);


--
-- TOC entry 3101 (class 0 OID 16573)
-- Dependencies: 210
-- Data for Name: poem_menu; Type: TABLE DATA; Schema: demo; Owner: -
--

INSERT INTO demo.poem_menu VALUES ('2023-09-20 07:09:42.724969', NULL, 43511453924630528, NULL, 61879920510980096, '新增部门', NULL, NULL, 1, 'dept:add', 56521040461266944, 2, 0, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-09-20 07:10:15.708015', NULL, 43511453924630528, NULL, 61880058860097536, '删除部门', NULL, NULL, 1, 'dept:remove', 56521040461266944, 2, 2, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-09-20 07:33:43.079945', NULL, 43511453924630528, NULL, 61885961797746688, '新增角色', NULL, NULL, 1, 'role:add', 54868755092041728, 2, 0, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-09-20 07:36:04.574444', NULL, 43511453924630528, NULL, 61886555274985472, '新增用户', NULL, NULL, 1, 'user:add', 55776168548691968, 2, 0, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-09-20 07:36:22.628288', '2023-09-20 07:36:29.501097', 43511453924630528, 43511453924630528, 61886630998949888, '编辑用户', NULL, NULL, 1, 'user:edit', 55776168548691968, 2, 1, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-09-20 07:37:14.381184', NULL, 43511453924630528, NULL, 61886848066764800, '删除用户', NULL, NULL, 1, 'user:remove', 55776168548691968, 2, 2, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES (NULL, '2023-09-20 07:37:46.333053', NULL, 43511453924630528, 2, '菜单管理', 'menu', 'menu', 1, 'menu', 1, 1, 0, 'system/menu/index.vue', 'menu', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-09-05 12:15:26.191703', '2023-09-20 07:37:46.33507', 43511453924630528, 43511453924630528, 56521040461266944, '部门管理', 'dept', 'dept', 1, 'dept', 1, 1, 1, 'system/dept/index.vue', 'usergroup', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-08-31 22:49:50.658071', '2023-09-20 07:37:46.33605', 43511453924630528, 43511453924630528, 54868755092041728, '角色管理', 'role', 'role', 1, 'role', 1, 1, 2, 'system/role/index.vue', 'add-circle', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-09-03 10:55:34.853426', '2023-09-20 07:37:46.337047', 43511453924630528, 43511453924630528, 55776168548691968, '用户管理', 'user', 'user', 1, 'user', 1, 1, 3, 'system/user/index.vue', 'personal-information', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-09-20 07:34:21.246774', '2023-09-20 07:46:53.251336', 43511453924630528, 43511453924630528, 61886121885941760, '编辑角色', NULL, NULL, 1, 'role:edit', 54868755092041728, 2, 1, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-09-20 07:09:59.222276', '2023-09-20 07:47:01.972269', 43511453924630528, 43511453924630528, 61879989708607488, '编辑部门', NULL, NULL, 1, 'dept:edit', 56521040461266944, 2, 1, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-10-16 00:02:54.422018', '2023-10-16 00:03:52.343', 43511453924630528, 43511453924630528, 71194596011335680, '编辑菜单', NULL, NULL, 1, 'menu:edit', 2, 2, 1, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-10-16 00:03:23.393285', '2023-10-16 00:03:52.343998', 43511453924630528, 43511453924630528, 71194717532905472, '删除菜单', NULL, NULL, 1, 'menu:remove', 2, 2, 2, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-08-26 20:27:38.199485', '2023-10-16 00:03:52.344996', 43511453924630528, 43511453924630528, 53021028150870016, '菜单详情', 'detailPage', 'detailPage', 1, '', 2, 0, 3, 'system/menu/detailPage.vue', '', 0, 1, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-10-16 13:49:19.685255', NULL, 43511453924630528, NULL, 71402571640487936, '数据字典', 'dict', 'dict', 1, 'dict', 1, 1, 4, 'system/dict/index.vue', 'address-book', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-10-16 13:50:11.321704', '2023-10-25 16:57:30.197491', 43511453924630528, 43511453924630528, 71402788263706624, '新增字典', NULL, NULL, 1, 'dict:add', 71402571640487936, 2, 0, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-10-16 13:50:40.957978', '2023-10-25 16:57:30.200493', 43511453924630528, 43511453924630528, 71402912566099968, '编辑字典', NULL, NULL, 1, 'dict:edit', 71402571640487936, 2, 1, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-10-18 15:33:58.652014', '2023-10-25 16:57:30.201491', 43511453924630528, 43511453924630528, 72153683310071808, '字典数据', 'dictData', 'dictData', 1, 'dict', 71402571640487936, 1, 2, 'system/dict/dictData.vue', '', 0, 1, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-10-16 13:51:09.427797', '2023-10-25 16:57:30.203486', 43511453924630528, 43511453924630528, 71403031977934848, '删除字典', NULL, NULL, 1, 'dict:remove', 71402571640487936, 2, 3, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-08-26 13:48:01.071347', '2023-11-05 13:51:24.46896', 43511453924630528, 43511453924630528, 52920460807962624, '新增菜单', 'addMenu', 'menuFrom', 1, 'menu:add', 2, 2, 0, 'system/menu/menuFrom.vue', 'add-circle', 0, 1, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-09-20 07:34:35.797543', '2023-11-05 13:52:05.638861', 43511453924630528, 43511453924630528, 61886182917259264, '删除角色', NULL, NULL, 1, 'role:remove', 54868755092041728, 2, 2, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-10-21 14:47:46.068381', '2023-11-05 13:53:41.828607', 43511453924630528, 43511453924630528, 73229217850961920, 'dd', NULL, NULL, 1, 'dd', 2, 2, 0, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-11-05 14:58:15.190638', NULL, 43511453924630528, NULL, 78667674553647104, '系统配置', 'systemParameter', 'systemParameter', 1, 'systemParameter', 78652370372997120, 1, 1, 'systemSetting/systemParameter/index.vue', 'setting', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES (NULL, '2023-11-05 13:57:36.155879', NULL, 43511453924630528, 1, '系统管理', 'system', 'system', 1, 'system', 0, 0, 0, 'LAYOUT', 'brightness', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-11-05 15:16:22.598757', '2023-11-05 15:17:33.385125', 43511453924630528, 43511453924630528, 78672235477540864, '添加配置', NULL, NULL, 1, 'systemParameter:add', 78667674553647104, 2, 0, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-11-05 15:16:38.623969', '2023-11-05 15:17:37.021361', 43511453924630528, 43511453924630528, 78672302695456768, '编辑配置', NULL, NULL, 1, 'systemParameter:edit', 78667674553647104, 2, 1, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-11-05 15:16:57.80267', '2023-11-05 15:17:39.646616', 43511453924630528, 43511453924630528, 78672383138013184, '删除配置', NULL, NULL, 1, 'systemParameter:remove', 78667674553647104, 2, 2, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-11-05 14:13:00.410588', NULL, 43511453924630528, NULL, 78656287945199616, '新增i18n', NULL, NULL, 1, 'i18n:add', 78652662837620736, 2, 0, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-11-05 14:16:20.355666', NULL, 43511453924630528, NULL, 78657126579507200, '编辑i18n', NULL, NULL, 1, 'i18n:edit', 78652662837620736, 2, 1, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-11-05 14:16:34.24195', NULL, 43511453924630528, NULL, 78657184821612544, '删除i18n', NULL, NULL, 1, 'i18n:remove', 78652662837620736, 2, 2, '', '', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-11-05 13:57:26.388083', '2023-11-05 14:40:16.733127', 43511453924630528, 43511453924630528, 78652370372997120, '系统设置', 'systemSetting', 'systemSetting', 1, NULL, 0, 0, 1, 'LAYOUT', 'data-base', 0, 0, 0, NULL);
INSERT INTO demo.poem_menu VALUES ('2023-11-05 13:58:36.116057', '2023-11-05 14:40:36.561825', 43511453924630528, 43511453924630528, 78652662837620736, '语言包管理', 'i18n', 'i18n', 1, 'i18n', 78652370372997120, 1, 0, 'systemSetting/i18n/index.vue', 'map-grid', 0, 0, 0, NULL);


--
-- TOC entry 3102 (class 0 OID 16585)
-- Dependencies: 211
-- Data for Name: poem_role; Type: TABLE DATA; Schema: demo; Owner: -
--

INSERT INTO demo.poem_role VALUES ('2023-08-10 16:22:00.297211', '2023-08-11 16:12:08.738062', NULL, NULL, 47161007073583104, '超级管理员', '', 'admin', 0, 56925072011030528);
INSERT INTO demo.poem_role VALUES ('2023-09-18 20:37:47.961258', '2023-09-19 06:10:04.672331', 43511453924630528, 43511453924630528, 61358506599022592, 'admin1', '', '2011', 3, 56925072011030528);
INSERT INTO demo.poem_role VALUES ('2023-08-11 16:12:38.450753', '2023-10-16 00:05:37.298946', 43511453924630528, 43511453924630528, 47521038386257920, '普通用户1', '普通用户', 'normal', 2, 56925072011030528);
INSERT INTO demo.poem_role VALUES ('2023-09-19 06:02:01.424283', '2023-10-16 00:05:48.241063', 61478072612352000, 43511453924630528, 61500498322751488, 'demo1创建的角色', '', 'd1r', 4, 56925072011030528);
INSERT INTO demo.poem_role VALUES ('2023-10-16 00:09:04.477519', NULL, 43511453924630528, NULL, 71196148142891008, 'demo', '1', '1', 4, 56925072011030528);
INSERT INTO demo.poem_role VALUES ('2023-10-21 14:43:22.528032', NULL, 43511453924630528, NULL, 73228112484085760, 'a', '', 'a', 4, 56925072011030528);


--
-- TOC entry 3103 (class 0 OID 16592)
-- Dependencies: 212
-- Data for Name: poem_role_dept; Type: TABLE DATA; Schema: demo; Owner: -
--



--
-- TOC entry 3104 (class 0 OID 16595)
-- Dependencies: 213
-- Data for Name: poem_role_menu; Type: TABLE DATA; Schema: demo; Owner: -
--

INSERT INTO demo.poem_role_menu VALUES (1, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (2, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (3, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (43533221221588992, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (43534000628129792, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (43534485992988672, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (43534727018668032, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46109515302531072, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46417473932570624, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46418317331607552, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46418526044368896, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46418690104569856, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46420850162741248, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46421021260984320, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46421125892091904, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46426345619533824, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46426573244411904, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46426810008678400, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46427115974766592, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46427303703425024, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46427606339235840, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46427781212352512, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46427956462956544, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46428489177313280, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46428763027615744, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46429086790135808, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46429210002010112, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46431086542008320, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46433755016933376, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46434100359147520, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46434283155304448, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46434397873713152, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46434491276668928, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (4, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (5, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (6, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46435242463932416, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46436717214773248, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46437512295428096, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46444644046643200, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46444019107930112, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46814807308828672, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46815187094667264, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46815080769060864, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (46814922723491840, 47161007073583104);
INSERT INTO demo.poem_role_menu VALUES (1, 55773234062618624);
INSERT INTO demo.poem_role_menu VALUES (2, 55773234062618624);
INSERT INTO demo.poem_role_menu VALUES (52920460807962624, 55773234062618624);
INSERT INTO demo.poem_role_menu VALUES (56521040461266944, 55773234062618624);
INSERT INTO demo.poem_role_menu VALUES (54868755092041728, 55773234062618624);
INSERT INTO demo.poem_role_menu VALUES (55776168548691968, 55773234062618624);
INSERT INTO demo.poem_role_menu VALUES (55776168548691968, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (1, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (61886630998949888, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (2, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (71194717532905472, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (61885961797746688, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (61879920510980096, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (61880058860097536, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (71194596011335680, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (61886121885941760, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (61886555274985472, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (61886848066764800, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (54868755092041728, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (52920460807962624, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (61886182917259264, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (61879989708607488, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (56521040461266944, 47521038386257920);
INSERT INTO demo.poem_role_menu VALUES (55776168548691968, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (1, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (61886630998949888, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (2, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (71194717532905472, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (61885961797746688, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (61879920510980096, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (61880058860097536, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (71194596011335680, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (55776168548691968, 61358506599022592);
INSERT INTO demo.poem_role_menu VALUES (61886121885941760, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (61886555274985472, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (61886848066764800, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (54868755092041728, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (52920460807962624, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (61886182917259264, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (61879989708607488, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (56521040461266944, 61500498322751488);
INSERT INTO demo.poem_role_menu VALUES (0, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (1, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (55776168548691968, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (2, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (61886630998949888, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (71194717532905472, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (61885961797746688, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (61879920510980096, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (61880058860097536, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (71194596011335680, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (53021028150870016, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (61886121885941760, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (61886555274985472, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (61886848066764800, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (54868755092041728, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (52920460807962624, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (61886182917259264, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (61879989708607488, 71196148142891008);
INSERT INTO demo.poem_role_menu VALUES (56521040461266944, 71196148142891008);


--
-- TOC entry 3105 (class 0 OID 16598)
-- Dependencies: 214
-- Data for Name: poem_user; Type: TABLE DATA; Schema: demo; Owner: -
--

INSERT INTO demo.poem_user VALUES ('2023-09-19 04:38:33.27902', '2023-11-05 00:20:25.254128', 43511453924630528, 43511453924630528, 61479492635906048, 'demo2', 'demo2', '8aead456e79fa8829bf2d2ea177e5445', 0, '837713748@qq.com', 58737314673807360, NULL, '59efffe60e734b12a8117fc2b653e0a2');
INSERT INTO demo.poem_user VALUES ('2023-08-10 16:23:55.162084', '2023-08-11 16:13:15.012373', 43511453924630528, 43511453924630528, 47161488860700672, 'aaaa', 'aaaa', '8aead456e79fa8829bf2d2ea177e5445', 0, '837713748@qq.com', 56925072011030528, NULL, '59efffe60e734b12a8117fc2b653e0a2');
INSERT INTO demo.poem_user VALUES ('2023-07-31 14:39:59.020972', '2023-08-11 16:32:51.85017', NULL, 43511453924630528, 43511453924630528, 'admin', 'admin', '8aead456e79fa8829bf2d2ea177e5445', 0, '', 56925072011030528, NULL, '59efffe60e734b12a8117fc2b653e0a2');
INSERT INTO demo.poem_user VALUES ('2023-09-19 04:32:54.718347', NULL, 43511453924630528, NULL, 61478072612352000, 'demo', 'demo1', '8aead456e79fa8829bf2d2ea177e5445', 0, '837713748@qq.com', 56925072011030528, NULL, '59efffe60e734b12a8117fc2b653e0a2');
INSERT INTO demo.poem_user VALUES ('2023-09-19 06:01:30.166149', '2023-09-19 14:10:17.753947', 61478072612352000, 43511453924630528, 61500367217197056, 'demo1创建的用户', 'd1demo1', '8aead456e79fa8829bf2d2ea177e5445', 0, '837713748@qq.com', 61499932322398208, NULL, '59efffe60e734b12a8117fc2b653e0a2');


--
-- TOC entry 3106 (class 0 OID 16604)
-- Dependencies: 215
-- Data for Name: poem_user_role; Type: TABLE DATA; Schema: demo; Owner: -
--

INSERT INTO demo.poem_user_role VALUES (43256994949713920, 43149693402112000);
INSERT INTO demo.poem_user_role VALUES (43267318545367040, 43149693402112000);
INSERT INTO demo.poem_user_role VALUES (43271312646848512, 43149693402112000);
INSERT INTO demo.poem_user_role VALUES (43493071414317056, 43276927817465856);
INSERT INTO demo.poem_user_role VALUES (43493071414317056, 43280811122212864);
INSERT INTO demo.poem_user_role VALUES (47161488860700672, 47521038386257920);
INSERT INTO demo.poem_user_role VALUES (43511453924630528, 47161007073583104);
INSERT INTO demo.poem_user_role VALUES (61369900169986048, 47521038386257920);
INSERT INTO demo.poem_user_role VALUES (61374726488489984, 61358506599022592);
INSERT INTO demo.poem_user_role VALUES (61380650506711040, 61358506599022592);
INSERT INTO demo.poem_user_role VALUES (61478072612352000, 47521038386257920);
INSERT INTO demo.poem_user_role VALUES (61479492635906048, 47521038386257920);
INSERT INTO demo.poem_user_role VALUES (61500367217197056, 47521038386257920);
INSERT INTO demo.poem_user_role VALUES (61500367217197056, 61358506599022592);
INSERT INTO demo.poem_user_role VALUES (71048041284894720, 61500498322751488);
INSERT INTO demo.poem_user_role VALUES (71048041284894720, 61358506599022592);


--
-- TOC entry 3107 (class 0 OID 16607)
-- Dependencies: 216
-- Data for Name: system_parameter; Type: TABLE DATA; Schema: demo; Owner: -
--

INSERT INTO demo.system_parameter VALUES ('2023-11-05 15:24:18.271433', '2023-11-05 15:27:44.175406', 43511453924630528, 43511453924630528, 78674230594658304, 'demo1', 'demo1', 'dd');
INSERT INTO demo.system_parameter VALUES ('2023-11-05 15:57:53.834973', NULL, 43511453924630528, NULL, 78682684474470400, 'default_user_password', '123456', '用默认密码');


--
-- TOC entry 2938 (class 2606 OID 16614)
-- Name: gen_table_column gen_table_column_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.gen_table_column
    ADD CONSTRAINT gen_table_column_pk PRIMARY KEY (column_id);


--
-- TOC entry 2936 (class 2606 OID 16616)
-- Name: gen_table gen_table_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.gen_table
    ADD CONSTRAINT gen_table_pk PRIMARY KEY (table_id);


--
-- TOC entry 2942 (class 2606 OID 16618)
-- Name: poem_dept_ancestors poem_dept_ancestors_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.poem_dept_ancestors
    ADD CONSTRAINT poem_dept_ancestors_pk PRIMARY KEY (dept_id, ancestors);


--
-- TOC entry 2940 (class 2606 OID 16620)
-- Name: poem_dept poem_dept_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.poem_dept
    ADD CONSTRAINT poem_dept_pk PRIMARY KEY (dept_id);


--
-- TOC entry 2944 (class 2606 OID 16622)
-- Name: poem_dict_data poem_dict_data_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.poem_dict_data
    ADD CONSTRAINT poem_dict_data_pk PRIMARY KEY (dict_code);


--
-- TOC entry 2946 (class 2606 OID 16624)
-- Name: poem_dict_type poem_dict_type_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.poem_dict_type
    ADD CONSTRAINT poem_dict_type_pk PRIMARY KEY (dict_type_id);


--
-- TOC entry 2948 (class 2606 OID 16626)
-- Name: poem_dict_type poem_dict_type_un; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.poem_dict_type
    ADD CONSTRAINT poem_dict_type_un UNIQUE (dict_type);


--
-- TOC entry 2950 (class 2606 OID 16628)
-- Name: poem_i18n poem_i18n_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.poem_i18n
    ADD CONSTRAINT poem_i18n_pk PRIMARY KEY (id);


--
-- TOC entry 2953 (class 2606 OID 16630)
-- Name: poem_menu poem_menu_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.poem_menu
    ADD CONSTRAINT poem_menu_pk PRIMARY KEY (menu_id);


--
-- TOC entry 2957 (class 2606 OID 16632)
-- Name: poem_role_dept poem_role_dept_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.poem_role_dept
    ADD CONSTRAINT poem_role_dept_pk PRIMARY KEY (role_id, dept_id);


--
-- TOC entry 2959 (class 2606 OID 16634)
-- Name: poem_role_menu poem_role_menu_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.poem_role_menu
    ADD CONSTRAINT poem_role_menu_pk PRIMARY KEY (menu_id, role_id);


--
-- TOC entry 2955 (class 2606 OID 16636)
-- Name: poem_role poem_role_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.poem_role
    ADD CONSTRAINT poem_role_pk PRIMARY KEY (role_id);


--
-- TOC entry 2961 (class 2606 OID 16638)
-- Name: poem_user poem_user_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.poem_user
    ADD CONSTRAINT poem_user_pk PRIMARY KEY (user_id);


--
-- TOC entry 2965 (class 2606 OID 16640)
-- Name: poem_user_role poem_user_role_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.poem_user_role
    ADD CONSTRAINT poem_user_role_pk PRIMARY KEY (user_id, role_id);


--
-- TOC entry 2963 (class 2606 OID 16642)
-- Name: poem_user poem_user_un; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.poem_user
    ADD CONSTRAINT poem_user_un UNIQUE (account);


--
-- TOC entry 2967 (class 2606 OID 16644)
-- Name: system_parameter system_parameter_pk; Type: CONSTRAINT; Schema: demo; Owner: -
--

ALTER TABLE ONLY demo.system_parameter
    ADD CONSTRAINT system_parameter_pk PRIMARY KEY (id);


--
-- TOC entry 2951 (class 1259 OID 16645)
-- Name: poem_i18n_unique; Type: INDEX; Schema: demo; Owner: -
--

CREATE UNIQUE INDEX poem_i18n_unique ON demo.poem_i18n USING btree (i18n_key, i18n_tag, language);


-- Completed on 2023-11-05 16:26:45

--
-- PostgreSQL database dump complete
--

