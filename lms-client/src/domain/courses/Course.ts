import type {Announcement} from "./Announcement.ts";
import type {Assignment} from "./Assignment.ts";

export interface Course {
    id: string;
    name: string;
    title: string;
    description: string;
    announcements: Announcement[];
    assignments: Assignment[];

}