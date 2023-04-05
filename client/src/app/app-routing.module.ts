import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SearchComponent } from './components/search/search.component';
import { ResultsComponent } from './components/results/results.component';
import { CommentComponent } from './components/comment/comment.component';

const routes: Routes = [
  { path: "", component: SearchComponent },
  { path: "search", component: ResultsComponent },
  { path: "comment/:title", component: CommentComponent },
  { path: "**", redirectTo: "/", pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
