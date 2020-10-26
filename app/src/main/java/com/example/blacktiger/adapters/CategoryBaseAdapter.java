/*
 * Copyright 2017 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.blacktiger.adapters;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by YanZhenjie on 2017/10/3.
 */
public abstract class CategoryBaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private LayoutInflater mInflater;

    public CategoryBaseAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public abstract void notifyDataSetChanged(List<String> dataList);

}