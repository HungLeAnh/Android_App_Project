package com.android_app_project.ui.dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android_app_project.R;
import com.android_app_project.adapter.CategoryAdapter;
import com.android_app_project.databinding.FragmentCategoryBinding;
import com.android_app_project.entities.Category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CATE_NAME_LIST = "cateNameList";

    // TODO: Rename and change types of parameters
    private List<Category> categoryList;

    private CategoryAdapter categoryAdapter;
    private FragmentCategoryBinding binding;
    private Context context;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(List<Category> childCategory) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(CATE_NAME_LIST, (Serializable) childCategory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryList = (List<Category>) getArguments().get(CATE_NAME_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        context = getContext();

        GetCategory();

        return root;
    }

    private void GetCategory() {
        categoryAdapter = new CategoryAdapter(categoryList,context);
        binding.categoryFragmentRv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager((context),LinearLayoutManager.VERTICAL,false);
        binding.categoryFragmentRv.setLayoutManager(layoutManager);
        binding.categoryFragmentRv.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }
}