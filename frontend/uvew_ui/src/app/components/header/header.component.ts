import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
 
})
export class HeaderComponent implements OnInit{
  
  isAuthenticated:boolean = false;
  constructor(private authService:AuthService ){}
  ngOnInit(): void {
    this.updateIsAuthenticated();
    }
  public async login(){
      await this.authService.login();
    }
  public async logout(){
      await this.authService.logout();
      }
  public updateIsAuthenticated(){
    this.authService.isAuthenticated().then((res)=>{
      this.isAuthenticated = res;
    });
  }
  public getUsername(){
      return this.authService.getUsername();
  } 
}
