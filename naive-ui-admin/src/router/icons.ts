import { renderIcon } from '@/utils/index';
import { DashboardOutlined } from '@vicons/antd';
import { GameControllerOutline } from '@vicons/ionicons5'

//前端路由图标映射表
export const constantRouterIcon = {
  DashboardOutlined: renderIcon(DashboardOutlined),
  GameControllerOutline: renderIcon(GameControllerOutline), 
};
