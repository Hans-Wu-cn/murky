import { useUserStore } from "@/store";
import { App } from "vue";

export default function (app: App<Element>) {
    app.directive('auth', {
        mounted(el: Element, binding: any) {
            if (!el) return;
            const { value } = binding
            const { userInfo } = useUserStore();
            const { permissions } = userInfo
            if (typeof value === 'string') {
                if (!permissions.includes(value)) {
                    // 没有权限
                    el.remove();
                }
            } else if (value && value instanceof Array) {
                let flag = true;
                for (let i = 0; i < value.length; i++) {
                    if(permissions.includes(value[i])){
                        flag = false;
                        return;
                    }
                }
                flag?el.remove():''
            }

        }
    })
}