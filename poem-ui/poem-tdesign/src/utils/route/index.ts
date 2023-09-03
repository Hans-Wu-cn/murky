import cloneDeep from 'lodash/cloneDeep';
import { shallowRef } from 'vue';

import { RouteItem, RouteMeta } from '@/api/auth/types';
import {
  BLANK_LAYOUT,
  EXCEPTION_COMPONENT,
  IFRAME,
  LAYOUT,
  PAGE_NOT_FOUND_ROUTE,
  PARENT_LAYOUT,
} from '@/utils/route/constant';

// vite 3+ support dynamic import from node_modules
const iconsPath = import.meta.glob('../../../node_modules/tdesign-icons-vue-next/esm/components/*.js');

const LayoutMap = new Map<string, () => Promise<typeof import('*.vue')>>();

LayoutMap.set('LAYOUT', LAYOUT);
LayoutMap.set('BLANK', BLANK_LAYOUT);
LayoutMap.set('IFRAME', IFRAME);

let dynamicViewsModules: Record<string, () => Promise<Recordable>>;

// 动态从包内引入单个Icon
// async function getMenuIcon(iconName: string) {
//   const RenderIcon = iconsPath[`../../../node_modules/tdesign-icons-vue-next/esm/components/${iconName}.js`];

//   const Icon = await RenderIcon();
//   // @ts-ignore
//   return shallowRef(Icon.default);
// }

// 动态引入路由组件
function asyncImportRoute(routes: RouteItem[] | undefined) {
  dynamicViewsModules = dynamicViewsModules || import.meta.glob('@/pages/**/*.vue');
  if (!routes) return;
  routes.forEach(async (item) => {
    const { component, name } = item;
    const { children } = item;

    if (component) {
      const layoutFound = LayoutMap.get(component.toUpperCase());
      if (layoutFound) {
        item.component = layoutFound;
      } else {
        item.component = dynamicImport(dynamicViewsModules, component);
      }
    } else if (name) {
      item.component = PARENT_LAYOUT();
    }
    // if (item.meta.icon) item.meta.icon = await getMenuIcon(item.meta.icon);

    // eslint-disable-next-line no-unused-expressions
    children && asyncImportRoute(children);
  });
}

function dynamicImport(dynamicViewsModules: Record<string, () => Promise<Recordable>>, component: string) {
  const viewsComponent = dynamicViewsModules[`/src/pages/${component}`];
  if (!viewsComponent) {
    console.warn(`Can't find ${component} in pages folder`);
    return EXCEPTION_COMPONENT;
  }
  return viewsComponent;
}

// 将背景对象变成路由对象
export function transformObjectToRoute<T = RouteItem>(routeList: RouteItem[]): T[] {
  routeList.forEach(async (route) => {
    const component = route.component as string;
    if (component) {
      if (component.toUpperCase() === 'LAYOUT') {
        route.component = LayoutMap.get(component.toUpperCase());
      } else {
        route.children = [cloneDeep(route)];
        route.component = LAYOUT;
        route.name = `${route.name}Parent`;
        route.path = '';
        route.meta = (route.meta || {}) as RouteMeta;
      }
    } else {
      throw new Error('component is undefined');
    }
    // eslint-disable-next-line no-unused-expressions
    route.children && asyncImportRoute(route.children);
    // if (route.meta.icon) route.meta.icon = await getMenuIcon(route.meta.icon);
  });

  return [PAGE_NOT_FOUND_ROUTE, ...routeList] as unknown as T[];
}
