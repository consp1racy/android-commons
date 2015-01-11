package net.xpece.commons.android.widget;

import android.support.v7.widget.SearchView;
import android.view.View;

/**
 * Created by pechanecjr on 21. 12. 2014.
 */
public class SearchViewCompatAdapter implements SearchView.OnCloseListener,
    SearchView.OnQueryTextListener, SearchView.OnSuggestionListener, View.OnClickListener {
  @Override
  public boolean onClose() {
    return false;
  }

  @Override
  public boolean onQueryTextSubmit(String s) {
    return false;
  }

  @Override
  public boolean onQueryTextChange(String s) {
    return false;
  }

  @Override
  public boolean onSuggestionSelect(int i) {
    return false;
  }

  @Override
  public boolean onSuggestionClick(int i) {
    return false;
  }

  @Override
  public void onClick(View v) {

  }
}
