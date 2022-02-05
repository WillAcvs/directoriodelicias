

import 'package:flutter/material.dart';
import 'package:directorio_delicias/models/category.dart';

class CategorySelectionItem extends StatelessWidget {
  final Category category;
  final bool isForList;

  const CategorySelectionItem({Key key, this.category, this.isForList = true})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 60.0,
      child: isForList
          ? Padding(
              child: _buildItem(
                context: context, 
                alignment: Alignment.center,
                color: Theme.of(context).textTheme.caption.color,
              ),
              padding: EdgeInsets.all(10.0),
            )
          : Padding(
              padding: EdgeInsets.only(top: 10),
              child: Stack(
                children: <Widget>[
                  Container(
                    decoration: BoxDecoration(
                      color: Theme.of(context).textTheme.bodyText1.color,
                      borderRadius: BorderRadius.circular(8),
                    ),
                    child: Padding(
                      padding: EdgeInsets.all(10),
                      child: _buildItem(
                        context: context, 
                        alignment: AlignmentDirectional.centerStart,
                        color: Theme.of(context).textTheme.caption.color,
                      ),
                    ),
                  ),
                  Align(
                    alignment: AlignmentDirectional.centerEnd,
                    child: Icon(
                      Icons.arrow_drop_down,
                      color: Theme.of(context).textTheme.caption.color,
                    ),
                  )
                ],
              ),
            ),
    );
  }

  _buildItem({BuildContext context, alignment, color}) {
    return Container(
      width: MediaQuery.of(context).size.width,
      alignment: alignment,
      child: Text(
        category.category,
        style: TextStyle(
          color: color
        ),
      ),
    );
  }
  
}