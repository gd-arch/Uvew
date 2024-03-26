import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { debounceTime, distinct, distinctUntilChanged, filter, fromEvent, map, merge } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { DataSharingService } from 'src/app/services/data-sharing.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
 
})
export class HeaderComponent implements OnInit,AfterViewInit{

  
  isAuthenticated:boolean = false;
  @ViewChild('searchInput') searchInput !:ElementRef;
  constructor(private authService:AuthService ,private dataSharingService:DataSharingService,private router: Router){}
  ngAfterViewInit(): void {
      
    const searchEnter = fromEvent<any>(this.searchInput.nativeElement, 'keyup').pipe(
      filter(event => event.key === 'Enter' || event.keyCode === 13),
      map(event => event.target.value),
      debounceTime(0)
    );
    searchEnter.subscribe(response =>{
      console.log(response)
      this.search(response);
    })
  }
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
  search(query:string){
    this.router.navigateByUrl('/dashboard');
    this.dataSharingService.setSearchTerm(query);
  }
}
