import type {Permission} from "./Permission.ts";

export interface Role {
    id: string;
    name: string;
    permissions: Permission[];
}