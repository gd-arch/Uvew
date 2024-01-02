import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userId!: string;

  constructor(private httpClient:HttpClient) { }
  registerUser() {
    this.httpClient.get("http://localhost:8080/api/user/register", {responseType: "text"})
      .subscribe(data => {
        this.userId = data;
      })
  }

  getUserId(): string {
    return this.userId;
  }
}
