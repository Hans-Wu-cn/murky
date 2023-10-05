import { useUserStore } from "@/store";

export const useAuth = (value:string|string[]):boolean=>{
    const { userInfo } = useUserStore();
    const { permissions } = userInfo;
    let flag = true
    if (typeof value === 'string') {
        if (!permissions.includes(value)) {
            // 没有权限
            flag = false
        }
    } else if (value && value instanceof Array) {
        flag = false;
        for (let i = 0; i < value.length; i++) {
            if(permissions.includes(value[i])){
                flag = true;
                return;
            }
        }
    }
    return flag
}