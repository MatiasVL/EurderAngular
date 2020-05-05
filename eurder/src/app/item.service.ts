import {Injectable} from '@angular/core';
import {Item} from "./item";
import {ITEMS} from "./mock-items";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor() {
  }

  getItems(): Observable<Item []> {
    return of(ITEMS);
  }

  getItem(id: string): Observable<Item> {
    return of(ITEMS.find(item => item.id === id));
  }
}
