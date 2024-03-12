import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private keycloakService: KeycloakService, 
    private router: Router) { }
    public async login() {
      this.keycloakService.login({
        redirectUri: window.location.origin ,
      });
      console.log("User authenticated");
    }
  
    public async logout() {
      this.keycloakService.logout();
    }
  
    public async isAuthenticated(){
      return this.keycloakService.isLoggedIn();
    }
    public getUsername():string{
      return this.keycloakService.getUsername();
    }
  
    
}
