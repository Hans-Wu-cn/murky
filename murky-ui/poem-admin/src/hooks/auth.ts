import { useUserStore } from "@/store";

export const hasAuth = (value: string | string[]): boolean => {
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
            if (permissions.includes(value[i])) {
                return true;
            }
        }
    }
    return flag
}

export const useAuth = (value: string | string[], element: any): any => {
    const { userInfo } = useUserStore();
    const { permissions } = userInfo;
    let flag = element
    if (typeof value === 'string') {
        if (!permissions.includes(value)) {
            // 没有权限
            flag = null
        }
    } else if (value && value instanceof Array) {
        flag = undefined;
        for (let i = 0; i < value.length; i++) {
            if (permissions.includes(value[i])) {
                return element;
            }
        }
    }
    return flag
}