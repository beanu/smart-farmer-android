package com.beanu.l3_common.widget;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.beanu.arad.support.recyclerview.OnItemClickListener;
import com.beanu.arad.support.recyclerview.divider.HorizontalDividerItemDecoration;
import com.beanu.l3_common.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lizhi
 * @date 2017/11/14.
 */

public class ActionSheet extends BottomSheetDialogFragment {

    private String id;
    private String title;
    private List<Pair<String, String>> menus;
    private Listener listener;
    private Map<String, Object> extData;

    public interface Listener {
        void onMenuClicked(String id, int clickIndex, Map<String, Object> extData);
    }

    public static class Builder {
        String id;
        String title;
        List<Pair<String, String>> menus;
        Listener listener;
        Map<String, Object> extData;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMenus(List<Pair<String, String>> menus) {
            this.menus = menus;
            return this;
        }

        public Builder setMenus(Pair<String, String>... menus) {
            this.menus = Arrays.asList(menus);
            return this;
        }

        public Builder setMenus2(List<String> menus) {
            if (this.menus == null) {
                this.menus = new ArrayList<>();
            } else {
                this.menus.clear();
            }
            for (String menu : menus) {
                this.menus.add(Pair.<String, String>create(menu, null));
            }
            return this;
        }

        public Builder setMenus(String... menus) {
            if (this.menus == null) {
                this.menus = new ArrayList<>();
            } else {
                this.menus.clear();
            }
            for (String menu : menus) {
                this.menus.add(Pair.<String, String>create(menu, null));
            }
            return this;
        }

        public Builder setMenus(Iterable<String> menus) {
            if (this.menus == null) {
                this.menus = new ArrayList<>();
            } else {
                this.menus.clear();
            }
            for (String menu : menus) {
                this.menus.add(Pair.<String, String>create(menu, null));
            }
            return this;
        }

        public Builder addMenu(Pair<String, String> menu) {
            if (this.menus == null) {
                menus = new ArrayList<>();
            }
            this.menus.add(menu);
            return this;
        }

        public Builder addMenu(String menuTitle, String menuSubtitle) {
            if (this.menus == null) {
                menus = new ArrayList<>();
            }
            this.menus.add(Pair.create(menuTitle, menuSubtitle));
            return this;
        }

        public Builder addMenu(String menu) {
            if (this.menus == null) {
                menus = new ArrayList<>();
            }
            this.menus.add(Pair.<String, String>create(menu, null));
            return this;
        }

        public Builder setListener(Listener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setExtData(Map<String, Object> extData) {
            this.extData = extData;
            return this;
        }

        public ActionSheet create() {
            ActionSheet actionSheet = new ActionSheet();
            actionSheet.title = title;
            actionSheet.menus = menus;
            actionSheet.listener = listener;
            actionSheet.extData = extData;
            return actionSheet;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_popup_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textTitle = view.findViewById(com.beanu.arad.R.id.text_title);
        TextView textCancel = view.findViewById(com.beanu.arad.R.id.text_cancel);
        final RecyclerView listMenus = view.findViewById(com.beanu.arad.R.id.list_menus);
        listMenus.setLayoutManager(new LinearLayoutManager(getContext()));
        listMenus.setAdapter(new MenusAdapter(menus));

        listMenus.addOnItemTouchListener(new OnItemClickListener(listMenus) {
            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int position) {
                if (listener != null && menus != null) {
                    listener.onMenuClicked(id, position, extData);
                }
                dismiss();
            }
        });
        listMenus.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(Color.parseColor("#dedfe0"))
                .size(1)
                .build());

        if (!TextUtils.isEmpty(title)) {
            textTitle.setVisibility(View.VISIBLE);
            textTitle.setText(title);
        }
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = dialog.getWindow();
            if (window != null) {
                //解决状态栏变纯黑的问题
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        return dialog;
    }

    private class MenusViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subtitle;

        MenusViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            this(inflater.inflate(R.layout.item_action_sheet_menu, parent, false));
        }

        MenusViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
        }

        void bind(List<Pair<String, String>> menus, int position) {
            Pair<String, String> item = menus.get(position);
            title.setText(item.first);
            if (TextUtils.isEmpty(item.second)) {
                subtitle.setVisibility(View.GONE);
            } else {
                subtitle.setVisibility(View.VISIBLE);
                subtitle.setText(item.second);
            }
        }
    }

    private class MenusAdapter extends RecyclerView.Adapter<MenusViewHolder> {
        List<Pair<String, String>> menus;
        LayoutInflater inflater;

        MenusAdapter(List<Pair<String, String>> menus) {
            this.menus = menus;
            this.inflater = LayoutInflater.from(getContext());
        }

        @Override
        public MenusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MenusViewHolder(inflater, parent, viewType);
        }

        @Override
        public void onBindViewHolder(MenusViewHolder holder, int position) {
            holder.bind(menus, position);
        }

        @Override
        public int getItemCount() {
            return menus == null ? 0 : menus.size();
        }
    }

}
