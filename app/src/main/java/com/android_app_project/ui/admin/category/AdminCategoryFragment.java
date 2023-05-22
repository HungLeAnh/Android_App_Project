package com.android_app_project.ui.admin.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android_app_project.Helper.CustomButtomonClickListener;
import com.android_app_project.Helper.SimpleSwipeHelper;
import com.android_app_project.R;
import com.android_app_project.adapter.ItemFavoriteAdapter;
import com.android_app_project.databinding.AdminFragmentCategoryBinding;
import com.android_app_project.databinding.FragmentFavoriteBinding;
import com.android_app_project.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class AdminCategoryFragment extends Fragment {
    Context context;
    private AdminFragmentCategoryBinding binding;

    public static AdminCategoryFragment newInstance() {
        return new AdminCategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = AdminFragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = getContext();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}