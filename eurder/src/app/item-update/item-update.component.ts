import {Component, Input, OnInit} from '@angular/core';
import {Item} from "../item";
import {ActivatedRoute} from "@angular/router";
import {ItemService} from "../item.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-item-update',
  templateUrl: './item-update.component.html',
  styleUrls: ['./item-update.component.css']
})

export class ItemUpdateComponent implements OnInit {
  @Input() item: Item;

  constructor(
    private route: ActivatedRoute,
    private itemService: ItemService,
    private location: Location
  ) {
  }

  ngOnInit(): void {
    this.getItem();
  }

  getItem(): void {
    const id=this.route.snapshot.paramMap.get('id');
    this.itemService.getItem(id).subscribe(item=>this.item=item);
  }

  goBack():void {
    this.location.back();
  }

  save():void {
    this.itemService.updateItem(this.item)
      .subscribe(()=>this.goBack());
  }
}
