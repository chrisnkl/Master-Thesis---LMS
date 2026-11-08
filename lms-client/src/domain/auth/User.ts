import type {Role} from "./Role.ts";

export interface User {
    id: string;
    username: string;
    email: string;
    roles: Role[];
    enabled: boolean;
    accountNonExpired: boolean;
    accountNonLocked: boolean;
    credentialsNonExpired: boolean;
    createdAt: string;
    updatedAt: string;
}