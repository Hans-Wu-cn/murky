import { defineStore } from 'pinia';
import { RouteRecordRaw } from 'vue-router';

import { getMenuList } from '@/api/auth';
import { Route, RouteItem } from '@/api/auth/types';
import { ResultEnum } from '@/enums/httpEnum';
import router, { fixedRouterList, homepageRouterList } from '@/router';
import { store } from '@/store';
import { transformObjectToRoute } from '@/utils/route';

export const usePermissionStore = defineStore('permission', {
  state: () => ({
    whiteListRouters: ['/login'],
    routers: [],
    removeRoutes: [],
    asyncRoutes: [],
  }),
  actions: {
    async initRoutes() {
      const accessedRouters = this.asyncRoutes;

      // 在菜单展示全部路由
      // this.routers = [...homepageRouterList, ...accessedRouters, ...fixedRouterList];
      // 在菜单只展示动态路由和首页
      // this.routers = [...homepageRouterList, ...accessedRouters];
      // 在菜单只展示动态路由
      this.routers = [...accessedRouters];
    },
    async buildAsyncRoutes() {
      try {
        // 发起菜单权限请求 获取菜单列表
        // const routes: Array<Route> = await getMenuList();
        const { result, code } = await getMenuList();
        if (ResultEnum.SUCCESS === code) {
          const asyncRoutes = buildRoutes(result);
          this.asyncRoutes = transformObjectToRoute(asyncRoutes);
        }
        await this.initRoutes();
        return this.asyncRoutes;
      } catch (error) {
        throw new Error("Can't build routes");
      }
    },
    async restoreRoutes() {
      // 不需要在此额外调用initRoutes更新侧边导肮内容，在登录后asyncRoutes为空会调用
      this.asyncRoutes.forEach((item: RouteRecordRaw) => {
        if (item.name) {
          router.removeRoute(item.name);
        }
      });
      this.asyncRoutes = [];
    },
  },
});

const buildRoutes = (routes: Array<Route>, parent?: RouteItem) => {
  return routes.map((item) => {
    const routeItem: RouteItem = {
      path: `${(parent && parent.path) ?? ''}/${item.path}`,
      name: item.name,
      component: item.component,
      meta: {
        title: item.label,
        icon: item.icon,
        hidden: item.isDisplay === 1,
        keepAlive: item.isCache === 1,
        frameSrc: item.isOutside === 1 ? item.path : undefined,
      },
    };
    // 为了防止出现后端返回结果不规范，处理有可能出现拼接出两个 反斜杠
    item.path = item.path.replace('//', '/');
    routeItem.children = buildRoutes(item.children, routeItem);
    return routeItem;
  });
};

export function getPermissionStore() {
  return usePermissionStore(store);
}
