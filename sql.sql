-- DROP SCHEMA public;

CREATE SCHEMA public AUTHORIZATION postgres;

COMMENT ON SCHEMA public IS 'standard public schema';
-- public.poem_menu definition

-- Drop table

-- DROP TABLE public.poem_menu;

CREATE TABLE public.poem_menu (
	menu_id int8 NOT NULL,
	"label" varchar NOT NULL, -- 菜单标题
	subtitle varchar NULL, -- 菜单副标题
	"path" varchar NOT NULL, -- 路径
	open_type int2 NOT NULL, -- 打开方式 1:当前窗口 2:新窗口
	auth varchar NULL, -- 菜单权限码
	create_time timestamp NULL, -- 创建时间
	update_time timestamp NULL, -- 修改时间
	create_user int8 NULL, -- 创建人
	update_user int8 NULL, -- 修改人
	parent_menu_id int8 NULL DEFAULT 0, -- 上级菜单id
	"type" int2 NULL, -- 菜单类型  0:目录 1:侧边菜单 2:按钮
	"order" int2 NULL DEFAULT 0,
	component varchar NULL, -- 组件路径
	icon varchar NULL, -- 目录图标
	CONSTRAINT poem_menu_pk PRIMARY KEY (menu_id)
);

-- Column comments

COMMENT ON COLUMN public.poem_menu."label" IS '菜单标题';
COMMENT ON COLUMN public.poem_menu.subtitle IS '菜单副标题';
COMMENT ON COLUMN public.poem_menu."path" IS '路径';
COMMENT ON COLUMN public.poem_menu.open_type IS '打开方式 1:当前窗口 2:新窗口';
COMMENT ON COLUMN public.poem_menu.auth IS '菜单权限码';
COMMENT ON COLUMN public.poem_menu.create_time IS '创建时间';
COMMENT ON COLUMN public.poem_menu.update_time IS '修改时间';
COMMENT ON COLUMN public.poem_menu.create_user IS '创建人';
COMMENT ON COLUMN public.poem_menu.update_user IS '修改人';
COMMENT ON COLUMN public.poem_menu.parent_menu_id IS '上级菜单id';
COMMENT ON COLUMN public.poem_menu."type" IS '菜单类型  0:目录 1:侧边菜单 2:按钮';
COMMENT ON COLUMN public.poem_menu.component IS '组件路径';
COMMENT ON COLUMN public.poem_menu.icon IS '目录图标';

-- Permissions

ALTER TABLE public.poem_menu OWNER TO postgres;
GRANT ALL ON TABLE public.poem_menu TO postgres;




-- Permissions

GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;
