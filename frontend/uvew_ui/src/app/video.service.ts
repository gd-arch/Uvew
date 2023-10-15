import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UploadVideoResponse } from './upload-video/UploadVideoResponse';

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
  public updateVideo(data:any){
    console.log(data);
    //create a typescript response
    return this.http.put<any>(this.url+"/api/videos",data);
  }
  
}
