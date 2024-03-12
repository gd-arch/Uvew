import { Video } from '../components/home/video-card/Video';

export interface VideoPageDto {
  content: Video[];
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  isLastPage: boolean;
}
