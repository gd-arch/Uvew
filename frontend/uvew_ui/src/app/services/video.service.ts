import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UploadVideoResponse } from '../components/upload/upload-video/UploadVideoResponse';
import { Observable } from 'rxjs';
import { Video } from '../components/home/video-card/Video';
import { VideoPageDto } from '../models/VideoPageDto';
@Injectable({
  providedIn: 'root'
})
export class VideoService {
  

  private url="http://localhost:8080";
  constructor(private http:HttpClient) { }

  public uploadVideo(file:any){
    const formData = new FormData();
    formData.append('file', file,file.name);
    return this.http.post<UploadVideoResponse>(this.url+"/api/videos", formData);
  }
  public updateVideo(data:Video){
    console.log(data);
    return this.http.put<any>(this.url+"/api/videos",data);
  }
   public uploadThumbnail(file:any,videoId:string){
    const formData = new FormData();
    formData.append('file', file,file.name);
    formData.append('id',videoId);
    return this.http.post<any>(this.url+"/api/videos/thumbnail",formData);
  }
  public getVideos(pageNo:number=0,pageSize:number=10,searchQuery:string=''):Observable<VideoPageDto>{
    let params = new HttpParams().set("pageNo",pageNo).set("pageSize", pageSize).set("searchQuery",searchQuery);
    return this.http.get<VideoPageDto>(this.url+"/api/videos",{params: params});
  }
  public getVideo(videoId: string): Observable<Video> {
    const url = `${this.url}/api/videos/${videoId}`;
    return this.http.get<Video>(url);
  }

  public likeVideo(videoId:string){
    return this.http.get(this.url+"/api/videos"+"/like/"+videoId);
  }
  public dislikeVideo(videoId:string){
    return this.http.get(this.url+"/api/videos"+"/dislike/"+videoId);
  }
  public viewVideo(videoId:string){
    return this.http.get(this.url+"/api/videos"+"/view/"+videoId);
  }


}
