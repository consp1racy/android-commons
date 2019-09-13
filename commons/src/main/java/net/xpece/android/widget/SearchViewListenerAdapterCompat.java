package net.xpece.android.widget;

import androidx.appcompat.widget.SearchView;
import android.view.View;

/**
 * Created by pechanecjr on 21. 12. 2014.
 */
public class SearchViewListenerAdapterCompat implements SearchView.OnCloseListener,
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
