import CommentDto from "./CommentDto";
export default interface CommentPageDto{
    content: CommentDto[];
    pageNumber: number;
    pageSize: number;
    totalElements: number;
    totalPages: number;
    lastPage: boolean;
}