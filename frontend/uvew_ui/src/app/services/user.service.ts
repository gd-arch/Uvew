import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userId!: string;
  private url: string  = "http://localhost:8080";
  constructor(private http:HttpClient) { }
  public registerUser() {
    this.http.get(this.url+"/api/user/register", {responseType: "text"})
      .subscribe(data => {
        this.userId = data;
      })
  }

  public getUserId(): string {
    return this.userId;
  }
  public subscribeToUser(userSubId:string){
    return this.http.get(this.url+"/api/user"+"/subscribe/"+userSubId);
  }
  public unsubscribeFromUser(userSubId:string){
    return this.http.get(this.url+"/api/user"+"/unsubscribe/"+userSubId);
  }
  public getTotalSubscribers(userId:string) {
    // return this.http.get(this.url+"/api/user/subscribers");
  }
}
