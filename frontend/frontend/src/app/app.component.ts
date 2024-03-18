import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { NavComponent } from './components/nav/nav.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, NavComponent],
  templateUrl: `app.component.html`,
  styles: [],
})
export class AppComponent {
  title = 'frontend';
}