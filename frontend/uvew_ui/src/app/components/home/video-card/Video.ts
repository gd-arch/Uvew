type VideoStatus = 'PUBLIC' | 'PRIVATE' | 'UNLISTED';

export interface Video {

    id:string;
    title: string ;
    userSubId: string;
    authorName: string;
    likes: number;
    dislikes: number;
    tags: string[];
    videoStatus: VideoStatus;
    videoCount: number;
    thumbnailUrl: string;
    videoUrl: string;
    commentList: Comment[];
    description:string;
    views:number;
    dateCreated:string;
}