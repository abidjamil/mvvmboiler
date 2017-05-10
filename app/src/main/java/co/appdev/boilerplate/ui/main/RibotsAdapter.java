package co.appdev.boilerplate.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.appdev.boilerplate.R;
import co.appdev.boilerplate.data.model.Users;

public class RibotsAdapter extends RecyclerView.Adapter<RibotsAdapter.RibotViewHolder> {

    private List<Users> mUsers;

    @Inject
    public RibotsAdapter() {
        mUsers = new ArrayList<>();
    }

    public void setRibots(List<Users> users) {
        mUsers = users;
    }

    @Override
    public RibotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ribot, parent, false);
        return new RibotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RibotViewHolder holder, int position) {
        Users users = mUsers.get(position);
        holder.nameTextView.setText(String.format("%s %s",
                users.getName().getFirst(), users.getName().getLast()));
        holder.emailTextView.setText(users.getEmail());
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class RibotViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view_hex_color) View hexColorView;
        @BindView(R.id.text_name) TextView nameTextView;
        @BindView(R.id.text_email) TextView emailTextView;

        public RibotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
