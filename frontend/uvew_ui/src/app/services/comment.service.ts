import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import CommentPageDto from '../components/home/comment-section/CommentPageDto';
import CommentDto from '../components/home/comment-section/CommentDto';
import CommentData from '../components/home/comment-section/CommentData';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private url="http://localhost:8080";

  constructor(private http:HttpClient) { }

  public getCommentsForVideo(videoId:string,pageNo:number=0,pageSize:number=10):Observable<CommentPageDto>{
    let params = new HttpParams().set("videoId",videoId).set("pageNo",pageNo).set("pageSize", pageSize);
    return this.http.get<CommentPageDto>(this.url+"/api/comments",{params:params});
  }

  public postComment(commentData:CommentData):Observable<any>{
    return this.http.post(this.url+"/api/comments",commentData);
  }
  
  
}
