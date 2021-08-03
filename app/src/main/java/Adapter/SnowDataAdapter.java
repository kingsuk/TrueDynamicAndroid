package Adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

//import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Domain.SnowResponse;
import ws.wolfsoft.cryptostar.R;

public class SnowDataAdapter extends RecyclerView.Adapter<SnowDataAdapter.ViewHolder> {

    // variable for our array list and context.
    private ArrayList<SnowResponse> userModalArrayList;
    private Context context;

    // creating a constructor.
    public SnowDataAdapter(ArrayList<SnowResponse> userModalArrayList, Context context) {
        this.userModalArrayList = userModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.snow_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // getting data from our array list in our modal class.
        SnowResponse snowResponse = userModalArrayList.get(position);

        // on below line we are setting data to our text view.
        holder.tvNumber.setText(snowResponse.number);
        holder.tvPriority.setText(snowResponse.priority);
        holder.tvGroupName.setText(snowResponse.assignmentGroupName);
        holder.tvAssignedName.setText(snowResponse.assignedToName);
        holder.tvUpdateDate.setText(snowResponse.sys_updated_on);
        holder.tvEndDate.setText(snowResponse.planned_end_date);
        holder.tvShortDescription.setText(snowResponse.short_description);
        // on below line we are loading our image
        // from url in our image view using picasso.
        //Picasso.get().load(userModal.getAvatar()).into(holder.userIV);
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return userModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating a variable for our text view and image view.
        private TextView tvPriority, tvNumber, tvEndDate, tvUpdateDate, tvGroupName, tvAssignedName, tvShortDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our variables.
            tvPriority = itemView.findViewById(R.id.tvPriority);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvEndDate = itemView.findViewById(R.id.tvEndDate);
            tvUpdateDate = itemView.findViewById(R.id.tvUpdateDate);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            tvAssignedName = itemView.findViewById(R.id.tvAssignedName);
            tvShortDescription = itemView.findViewById(R.id.tvShortDescription);
        }
    }
}
