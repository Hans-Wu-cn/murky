export interface BasicPageParams {
    pageNumber: number;
    pageSize: number;
    totalPage: number;
}

export interface PoemUserFrom {
    userId?: number;
    userName: string;
    account: string;
    password: string;
    sex: number;
    email?: string;
    roleIds: number[];
}

export interface PoemUserResponse{
    userId?: number;
    userName: string;
    account: string;
    password: string;
    sex: number;
    email?: string;
    roleIds: number[];
}