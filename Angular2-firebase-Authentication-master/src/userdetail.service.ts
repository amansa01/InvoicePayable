import {AngularFire} from 'angularfire2';
import {Injectable} from '@angular/core';
@Injectable()

export class UserDetailsService {
  title = 'app works';
  user ;
    provider = null;

  constructor(private af: AngularFire) {
    this.af.auth.subscribe(user => {
      if (user) {
        console.log('in User Details', user)
        this.user = user;
      }
    });
  }

  setDetails(userDetails: any) {
    this.user = userDetails;
  }
  getUserDetails(){
    console.log('get user Details', this.user);
    return this.user;
  }
}
