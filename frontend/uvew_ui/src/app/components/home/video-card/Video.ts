type VideoStatus = 'draft' | 'published' | 'archived';

export interface Video {
    id:string;
    title: string ;
    userId: string;
    likes: number;
    dislikes: number;
    tags: string[];
    videoStatus: VideoStatus;
    videoCount: number;
    thumbnailUrl: string;
    videoUrl: string;
    commentList: Comment[];
    description:string;
}