export interface Assignment {
    id: string;
    title: string;
    type: AssignmentType;
    files: string[];
    dueAt: string;
    createdBy: string;
    createdAt: string;
    updatedBy: string;
    updatedAt: string;
}

export type AssignmentType = "normal" | "quiz";

export interface AssignmentSubmission {
    id: string;
    files: string[];
    comment: string;
}