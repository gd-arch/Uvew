enum VideoStatus {
    PUBLIC = "PUBLIC",
    PRIVATE = "PRIVATE",
    UNLISTED = "UNLISTED"
  }
  
 export type VideoDto = {
    id: string;
    title: string;
    tags: string[];
    videoStatus: VideoStatus; // Use the VideoStatus enum type
    thumbnailUrl: string;
  };