import { renderIcon } from '@/utils/index';
import { DashboardOutlined } from '@vicons/antd';
import { LogoWebComponent,GameControllerOutline,Bug,Build,RadioButtonOnOutline,BarChart,Checkbox,Clipboard,Barcode,ColorFill } from '@vicons/ionicons5'

//前端路由图标映射表
export const constantRouterIcon = {
  DashboardOutlined: renderIcon(DashboardOutlined),
  GameControllerOutline: renderIcon(GameControllerOutline), 
  Bug: renderIcon(Bug), 
  Build: renderIcon(Build),
  RadioButtonOnOutline: renderIcon(RadioButtonOnOutline),
  BarChart: renderIcon(BarChart),
  Checkbox: renderIcon(Checkbox),
  Clipboard: renderIcon(Clipboard),
  Barcode: renderIcon(Barcode),
  ColorFill: renderIcon(ColorFill),
  LogoWebComponent: renderIcon(LogoWebComponent),
};
