import type {Role} from "./Role.ts";

export interface User {
    id: string;
    username: string;
    email: string;
    roles: Role[];
}