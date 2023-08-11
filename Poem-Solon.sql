--
-- PostgreSQL database dump
--

-- Dumped from database version 12.15 (Ubuntu 12.15-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 14.2

-- Started on 2023-08-11 18:51:41

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
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2965 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 202 (class 1259 OID 16385)
-- Name: poem_menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.poem_menu (
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    create_user bigint,
    update_user bigint,
    menu_id bigint NOT NULL,
    label character varying NOT NULL,
    subtitle character varying,
    path character varying NOT NULL,
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


ALTER TABLE public.poem_menu OWNER TO postgres;

--
-- TOC entry 2966 (class 0 OID 0)
-- Dependencies: 202
-- Name: TABLE poem_menu; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.poem_menu IS '菜单/权限表';


--
-- TOC entry 2967 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.label; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.label IS '菜单标题';


--
-- TOC entry 2968 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.subtitle; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.subtitle IS '菜单副标题';


--
-- TOC entry 2969 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.path; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.path IS '路由地址';


--
-- TOC entry 2970 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.open_type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.open_type IS '打开方式 1:当前窗口 2:新窗口';


--
-- TOC entry 2971 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.auth; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.auth IS '菜单权限码';


--
-- TOC entry 2972 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.create_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.create_time IS '创建时间';


--
-- TOC entry 2973 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.update_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.update_time IS '修改时间';


--
-- TOC entry 2974 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.create_user; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.create_user IS '创建人';


--
-- TOC entry 2975 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.update_user; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.update_user IS '修改人';


--
-- TOC entry 2976 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.parent_menu_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.parent_menu_id IS '上级菜单id';


--
-- TOC entry 2977 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.type IS '菜单类型  0:目录 1:侧边菜单 2:按钮';


--
-- TOC entry 2978 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.component; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.component IS '组件路径';


--
-- TOC entry 2979 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.icon; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.icon IS '目录图标';


--
-- TOC entry 2980 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.is_cache; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.is_cache IS '是否开启缓存 0:关闭  1:开启';


--
-- TOC entry 2981 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.is_display; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.is_display IS '是否显示在菜单  0:显示  1:隐藏';


--
-- TOC entry 2982 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.is_outside; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.is_outside IS '是否外链  0:否  1:是';


--
-- TOC entry 2983 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN poem_menu.query; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_menu.query IS '路由参数';


--
-- TOC entry 203 (class 1259 OID 24576)
-- Name: poem_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.poem_role (
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    create_user bigint,
    update_user bigint,
    role_id bigint NOT NULL,
    role_name character varying NOT NULL,
    describe character varying,
    role_code character varying NOT NULL
);


ALTER TABLE public.poem_role OWNER TO postgres;

--
-- TOC entry 2984 (class 0 OID 0)
-- Dependencies: 203
-- Name: TABLE poem_role; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.poem_role IS '角色表';


--
-- TOC entry 2985 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN poem_role.role_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_role.role_id IS '角色id';


--
-- TOC entry 2986 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN poem_role.role_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_role.role_name IS '角色名';


--
-- TOC entry 2987 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN poem_role.describe; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_role.describe IS '描述';


--
-- TOC entry 2988 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN poem_role.create_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_role.create_time IS '创建时间';


--
-- TOC entry 2989 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN poem_role.update_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_role.update_time IS '修改时间';


--
-- TOC entry 2990 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN poem_role.create_user; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_role.create_user IS '创建人';


--
-- TOC entry 2991 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN poem_role.update_user; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_role.update_user IS '修改人';


--
-- TOC entry 204 (class 1259 OID 24584)
-- Name: poem_role_menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.poem_role_menu (
    menu_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.poem_role_menu OWNER TO postgres;

--
-- TOC entry 2992 (class 0 OID 0)
-- Dependencies: 204
-- Name: TABLE poem_role_menu; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.poem_role_menu IS '角色菜单关系表';


--
-- TOC entry 2993 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN poem_role_menu.menu_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_role_menu.menu_id IS '菜单id';


--
-- TOC entry 2994 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN poem_role_menu.role_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_role_menu.role_id IS '角色id';


--
-- TOC entry 205 (class 1259 OID 24589)
-- Name: poem_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.poem_user (
    create_time timestamp without time zone,
    update_time timestamp without time zone,
    create_user bigint,
    update_user bigint,
    user_id bigint NOT NULL,
    user_name character varying NOT NULL,
    account character varying NOT NULL,
    password character varying NOT NULL,
    sex smallint NOT NULL,
    email character varying
);


ALTER TABLE public.poem_user OWNER TO postgres;

--
-- TOC entry 2995 (class 0 OID 0)
-- Dependencies: 205
-- Name: TABLE poem_user; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.poem_user IS '用户表';


--
-- TOC entry 2996 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_user.user_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_user.user_id IS '用户id';


--
-- TOC entry 2997 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_user.user_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_user.user_name IS '用户名';


--
-- TOC entry 2998 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_user.account; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_user.account IS '账号';


--
-- TOC entry 2999 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_user.password; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_user.password IS '密码';


--
-- TOC entry 3000 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_user.sex; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_user.sex IS '性别 0:男性 1:女性 2:其他';


--
-- TOC entry 3001 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_user.email; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_user.email IS '邮箱';


--
-- TOC entry 3002 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_user.create_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_user.create_time IS '创建时间';


--
-- TOC entry 3003 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_user.update_time; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_user.update_time IS '修改时间';


--
-- TOC entry 3004 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_user.create_user; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_user.create_user IS '创建人';


--
-- TOC entry 3005 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN poem_user.update_user; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_user.update_user IS '修改人';


--
-- TOC entry 206 (class 1259 OID 32768)
-- Name: poem_user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.poem_user_role (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.poem_user_role OWNER TO postgres;

--
-- TOC entry 3006 (class 0 OID 0)
-- Dependencies: 206
-- Name: TABLE poem_user_role; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.poem_user_role IS '用户角色关系表';


--
-- TOC entry 3007 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN poem_user_role.user_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_user_role.user_id IS '用户id';


--
-- TOC entry 3008 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN poem_user_role.role_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.poem_user_role.role_id IS '角色id';


--
-- TOC entry 2955 (class 0 OID 16385)
-- Dependencies: 202
-- Data for Name: poem_menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.poem_menu VALUES (46437512295428096, 'NaiveAdmin', 'NaiveAdmin', 'https://www.naiveadmin.com', 1, 'NaiveAdmin', '2023-08-08 16:27:05.698907', '2023-08-08 16:47:50.469622', NULL, NULL, 46436717214773248, 0, 0, 'IFRAME', '', 0, 0, 1, NULL);
INSERT INTO public.poem_menu VALUES (46426810008678400, '基础表格', '基础表格', 'basic', 1, 'comp:table:basic', '2023-08-08 15:44:34.074715', NULL, NULL, NULL, 46426573244411904, 1, 0, 'comp/table/basic.vue', 'DashboardOutlined', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46417473932570624, 'ddd', '', '', 1, 'ddd', '2023-08-08 15:07:28.182132', NULL, NULL, NULL, 46109515302531072, 2, 0, '', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (43534485992988672, '角色权限管理', '角色权限管理', 'role', 1, 'role', '2023-07-31 16:11:30.292246', '2023-08-07 18:13:29.320661', NULL, NULL, 43533221221588992, 1, 1, 'system/role/role.vue', 'GameControllerOutline', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46427115974766592, '单元格编辑', 'editCell', 'editCell', 1, 'comp:table:editCell', '2023-08-08 15:45:47.022895', '2023-08-08 15:45:55.366103', NULL, NULL, 46426573244411904, 1, 1, 'comp/table/editCell.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46427303703425024, '整行编辑', 'editRow', 'editRow', 1, 'comp:table:editRow', '2023-08-08 15:46:31.780629', '2023-08-08 15:46:38.934534', NULL, NULL, 46426573244411904, 1, 3, 'comp/table/editRow.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (1, '主控台', 'console', 'console', 1, 'console', NULL, '2023-08-08 15:56:07.562443', NULL, NULL, 2, 1, 0, 'dashboard/console/console.vue', 'DashboardOutlined', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (3, '工作台', 'workplace', 'workplace', 1, 'workplace', NULL, '2023-08-08 15:56:31.44124', NULL, NULL, 2, 1, 1, 'dashboard/workplace/workplace.vue', 'DashboardOutlined', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (2, 'Dashboard', 'dashboard', 'dashboard', 1, 'dashboard', NULL, '2023-08-08 16:16:28.522327', NULL, NULL, 0, 0, 0, 'LAYOUT', 'DashboardOutlined', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46109515302531072, '测试', '测试', 'system1', 1, 'system1', '2023-08-07 18:43:45.122033', '2023-08-08 16:16:28.523327', NULL, NULL, 0, 0, 1, '', 'GameControllerOutline', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46427781212352512, '基础使用', 'basic', 'basic', 1, 'comp:form:basic', '2023-08-08 15:48:25.627156', '2023-08-08 15:49:16.357811', NULL, NULL, 46427606339235840, 1, 0, 'comp/form/basic.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46427956462956544, 'useForm', 'useForm', 'useForm', 1, 'comp:form:useForm', '2023-08-08 15:49:07.411419', '2023-08-08 15:49:16.3598', NULL, NULL, 46427606339235840, 1, 1, 'comp/form/useForm.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (43533221221588992, '系统管理', '系统管理', '/system', 1, 'system', '2023-07-31 16:06:28.747328', '2023-08-08 16:16:28.523327', NULL, NULL, 0, 0, 2, 'LAYOUT', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46418317331607552, '设置页面', 'setting', 'setting', 1, 'setting', '2023-08-08 15:10:49.262609', '2023-08-08 16:16:28.523327', NULL, NULL, 0, 0, 3, 'LAYOUT', 'DashboardOutlined', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46420850162741248, '前端示例', '前端示例', 'example', 1, 'example', '2023-08-08 15:20:53.136947', '2023-08-08 16:16:28.523327', NULL, NULL, 0, 0, 4, 'LAYOUT', 'DashboardOutlined', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46444644046643200, 'NaiveUi(内嵌)', 'frame-naive', 'https://www.naiveui.com', 1, 'frame-naive', '2023-08-08 16:55:26.040949', '2023-08-08 16:55:46.207302', NULL, NULL, 46436717214773248, 0, 0, 'IFRAME', '', 0, 0, 1, NULL);
INSERT INTO public.poem_menu VALUES (46426573244411904, '表格', 'table', 'table', 1, 'comp:table', '2023-08-08 15:43:37.625628', '2023-08-08 15:54:19.119167', NULL, NULL, 46426345619533824, 0, 0, 'PARENTLAYOUT', 'DashboardOutlined', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46427606339235840, '表单', 'form', 'form', 1, 'comp:form', '2023-08-08 15:47:43.934012', '2023-08-08 15:54:19.121165', NULL, NULL, 46426345619533824, 0, 1, 'PARENTLAYOUT', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46428489177313280, '上传图片', 'upload', 'upload', 1, 'comp:upload', '2023-08-08 15:51:14.419617', '2023-08-08 15:54:19.121165', NULL, NULL, 46426345619533824, 1, 2, 'comp/upload/index.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46428763027615744, '弹窗扩展', 'modal', 'modal', 1, 'comp:modal', '2023-08-08 15:52:19.71007', '2023-08-08 15:54:19.122169', NULL, NULL, 46426345619533824, 1, 3, 'comp/modal/index.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46429086790135808, '富文本', 'richtext', 'richtext', 1, 'comp:richtext', '2023-08-08 15:53:36.90166', '2023-08-08 15:54:19.122169', NULL, NULL, 46426345619533824, 1, 4, 'comp/richtext/vue-quill.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46429210002010112, '拖拽', 'Drag', 'Drag', 1, 'comp:drag', '2023-08-08 15:54:06.278964', '2023-08-08 15:54:19.122169', NULL, NULL, 46426345619533824, 1, 5, 'comp/drag/index.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46444019107930112, '项目文档(内嵌)', 'frame-docs', 'https://jekip.github.io/docs', 1, 'frame-docs', '2023-08-08 16:52:57.044303', '2023-08-08 16:55:48.012441', NULL, NULL, 46436717214773248, 0, 0, 'IFRAME', '', 0, 0, 1, NULL);
INSERT INTO public.poem_menu VALUES (5, '基础表单', 'basic-form', 'basic-form', 1, 'basic-form', NULL, '2023-08-08 16:18:31.827922', NULL, NULL, 4, 1, 0, 'form/basicForm/index.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (6, '分步表单', 'step-form', 'step-form', 1, 'step-form', NULL, '2023-08-08 16:18:31.829922', NULL, NULL, 4, 1, 1, 'form/stepForm/stepForm.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46435242463932416, '表单详情', 'form-detail', 'detail', 1, 'detail', '2023-08-08 16:18:04.528718', '2023-08-08 16:18:31.829922', NULL, NULL, 4, 1, 2, 'form/detail/index.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46434283155304448, '403', 'exception-403', '403', 1, '403', '2023-08-08 16:14:15.811657', NULL, NULL, NULL, 46434100359147520, 1, 0, 'exception/403.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46434397873713152, '404', 'exception-404', '404', 1, '404', '2023-08-08 16:14:43.162674', NULL, NULL, NULL, 46434100359147520, 1, 0, 'exception/404.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46434491276668928, '500', 'exception-500', '500', 1, '500', '2023-08-08 16:15:05.431551', NULL, NULL, NULL, 46434100359147520, 1, 0, 'exception/500.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46421125892091904, '关于项目', '关于项目', 'index', 1, 'index', '2023-08-08 15:21:58.87508', NULL, NULL, NULL, 46421021260984320, 1, 0, 'about/index.vue', 'GameControllerOutline', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (43534727018668032, '用户权限管理', '用户权限管理', 'user', 1, 'user', '2023-07-31 16:12:27.757643', '2023-07-31 16:12:34.178427', NULL, NULL, 43533221221588992, 1, 2, 'system/user/user.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46418526044368896, '个人设置', 'account', 'account', 1, 'setting:account', '2023-08-08 15:11:39.023613', NULL, NULL, NULL, 46418317331607552, 1, 0, 'setting/account/account.vue', 'GameControllerOutline', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46418690104569856, '系统设置', 'system', 'system', 1, 'setting:system', '2023-08-08 15:12:18.138098', '2023-08-08 15:12:36.804149', NULL, NULL, 46418317331607552, 1, 0, 'setting/system/system.vue', 'DashboardOutlined', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46814922723491840, '成功页', 'result-success', 'result-success', 1, 'result-success', '2023-08-09 17:26:47.356389', NULL, NULL, NULL, 46814807308828672, 1, 0, 'result/success.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46815080769060864, '失败页', 'result-fail', 'result-fail', 1, 'result-fail', '2023-08-09 17:27:25.037686', NULL, NULL, NULL, 46814807308828672, 1, 0, 'result/fail.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46815187094667264, '信息页', 'result-info', 'result-info', 1, 'result-info', '2023-08-09 17:27:50.387364', NULL, NULL, NULL, 46814807308828672, 1, 0, 'result/info.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (43534000628129792, '菜单权限管理', '菜单权限', 'menu', 1, 'menu', '2023-07-31 16:09:34.572701', '2023-08-09 18:01:15.582521', NULL, 43511453924630528, 43533221221588992, 1, 0, 'system/menu/menu.vue', 'DashboardOutlined', 0, 0, 0, '');
INSERT INTO public.poem_menu VALUES (46421021260984320, '关于', 'about', 'about', 1, 'about', '2023-08-08 15:21:33.929521', '2023-08-09 17:26:24.22785', NULL, NULL, 46420850162741248, 0, 0, 'PARENTLAYOUT', 'DashboardOutlined', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46426345619533824, '组件示例', '组件示例', 'comp', 1, 'comp', '2023-08-08 15:42:43.355618', '2023-08-09 17:26:24.22985', NULL, NULL, 46420850162741248, 0, 1, 'PARENTLAYOUT', 'DashboardOutlined', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46431086542008320, '指令示例', 'directive', 'directive', 1, 'directive', '2023-08-08 16:01:33.679723', '2023-08-09 17:26:24.22985', NULL, NULL, 46420850162741248, 1, 2, 'directive/index.vue', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46433755016933376, '项目文档', 'external', 'https://docs.naiveadmin.com', 1, 'external', '2023-08-08 16:12:09.893893', '2023-08-09 17:26:24.22985', NULL, NULL, 46420850162741248, 0, 3, 'PARENTLAYOUT', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46434100359147520, '异常页面', 'exception', 'exception', 1, 'exception', '2023-08-08 16:13:32.229131', '2023-08-09 17:26:24.23085', NULL, NULL, 46420850162741248, 0, 4, 'PARENTLAYOUT', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (4, '表单页面', 'form', '/form/basic-form', 1, 'form', NULL, '2023-08-09 17:26:24.23085', NULL, NULL, 46420850162741248, 0, 5, 'PARENTLAYOUT', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46436717214773248, '外部页面', 'Frame', 'frame', 1, 'frame', '2023-08-08 16:23:56.136547', '2023-08-09 17:26:24.23085', NULL, NULL, 46420850162741248, 0, 6, 'PARENTLAYOUT', '', 0, 0, 0, NULL);
INSERT INTO public.poem_menu VALUES (46814807308828672, '结果页面', 'result', 'result', 1, 'result', '2023-08-09 17:26:19.839899', '2023-08-09 17:26:24.231852', NULL, NULL, 46420850162741248, 0, 7, 'PARENTLAYOUT', '', 0, 0, 0, NULL);


--
-- TOC entry 2956 (class 0 OID 24576)
-- Dependencies: 203
-- Data for Name: poem_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.poem_role VALUES (47521038386257920, '普通用户', '普通用户', '2023-08-11 16:12:38.450753', '2023-08-11 16:12:53.565233', 43511453924630528, 43511453924630528, 'normal');
INSERT INTO public.poem_role VALUES (47161007073583104, '超级管理员', '', '2023-08-10 16:22:00.297211', '2023-08-11 16:12:08.738062', NULL, NULL, 'admin');


--
-- TOC entry 2957 (class 0 OID 24584)
-- Dependencies: 204
-- Data for Name: poem_role_menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.poem_role_menu VALUES (2, 47521038386257920);
INSERT INTO public.poem_role_menu VALUES (1, 47521038386257920);
INSERT INTO public.poem_role_menu VALUES (3, 47521038386257920);
INSERT INTO public.poem_role_menu VALUES (46418317331607552, 47521038386257920);
INSERT INTO public.poem_role_menu VALUES (46418526044368896, 47521038386257920);
INSERT INTO public.poem_role_menu VALUES (46418690104569856, 47521038386257920);
INSERT INTO public.poem_role_menu VALUES (46109515302531072, 47521038386257920);
INSERT INTO public.poem_role_menu VALUES (46417473932570624, 47521038386257920);
INSERT INTO public.poem_role_menu VALUES (43533221221588992, 47521038386257920);
INSERT INTO public.poem_role_menu VALUES (43534000628129792, 47521038386257920);
INSERT INTO public.poem_role_menu VALUES (43534485992988672, 47521038386257920);
INSERT INTO public.poem_role_menu VALUES (43534727018668032, 47521038386257920);
INSERT INTO public.poem_role_menu VALUES (1, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (2, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (3, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (43533221221588992, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (43534000628129792, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (43534485992988672, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (43534727018668032, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46109515302531072, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46417473932570624, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46418317331607552, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46418526044368896, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46418690104569856, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46420850162741248, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46421021260984320, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46421125892091904, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46426345619533824, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46426573244411904, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46426810008678400, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46427115974766592, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46427303703425024, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46427606339235840, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46427781212352512, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46427956462956544, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46428489177313280, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46428763027615744, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46429086790135808, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46429210002010112, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46431086542008320, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46433755016933376, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46434100359147520, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46434283155304448, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46434397873713152, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46434491276668928, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (4, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (5, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (6, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46435242463932416, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46436717214773248, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46437512295428096, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46444644046643200, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46444019107930112, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46814807308828672, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46815187094667264, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46815080769060864, 47161007073583104);
INSERT INTO public.poem_role_menu VALUES (46814922723491840, 47161007073583104);


--
-- TOC entry 2958 (class 0 OID 24589)
-- Dependencies: 205
-- Data for Name: poem_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.poem_user VALUES (47161488860700672, 'aaaa', 'aaaa', '123456', 0, '837713748@qq.com', '2023-08-10 16:23:55.162084', '2023-08-11 16:13:15.012373', 43511453924630528, 43511453924630528);
INSERT INTO public.poem_user VALUES (43511453924630528, 'admin', 'admin', '123456', 0, '', '2023-07-31 14:39:59.020972', '2023-08-11 16:32:51.85017', NULL, 43511453924630528);


--
-- TOC entry 2959 (class 0 OID 32768)
-- Dependencies: 206
-- Data for Name: poem_user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.poem_user_role VALUES (43256994949713920, 43149693402112000);
INSERT INTO public.poem_user_role VALUES (43267318545367040, 43149693402112000);
INSERT INTO public.poem_user_role VALUES (43271312646848512, 43149693402112000);
INSERT INTO public.poem_user_role VALUES (43493071414317056, 43276927817465856);
INSERT INTO public.poem_user_role VALUES (43493071414317056, 43280811122212864);
INSERT INTO public.poem_user_role VALUES (47161488860700672, 47521038386257920);
INSERT INTO public.poem_user_role VALUES (43511453924630528, 47161007073583104);


--
-- TOC entry 2820 (class 2606 OID 16394)
-- Name: poem_menu poem_menu_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.poem_menu
    ADD CONSTRAINT poem_menu_pk PRIMARY KEY (menu_id);


--
-- TOC entry 2824 (class 2606 OID 24588)
-- Name: poem_role_menu poem_role_menu_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.poem_role_menu
    ADD CONSTRAINT poem_role_menu_pk PRIMARY KEY (menu_id, role_id);


--
-- TOC entry 2822 (class 2606 OID 24583)
-- Name: poem_role poem_role_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.poem_role
    ADD CONSTRAINT poem_role_pk PRIMARY KEY (role_id);


--
-- TOC entry 2826 (class 2606 OID 24596)
-- Name: poem_user poem_user_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.poem_user
    ADD CONSTRAINT poem_user_pk PRIMARY KEY (user_id);


--
-- TOC entry 2828 (class 2606 OID 32772)
-- Name: poem_user_role poem_user_role_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.poem_user_role
    ADD CONSTRAINT poem_user_role_pk PRIMARY KEY (user_id, role_id);


-- Completed on 2023-08-11 18:51:41

--
-- PostgreSQL database dump complete
--

