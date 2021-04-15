package com.example.files_practice.images;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.files_practice.Constants;
import com.example.files_practice.FileUtil;
import com.example.files_practice.R;
import com.example.files_practice.databinding.FragmentImagesBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ImagesFragment extends Fragment {
    public static final String TAG = ImagesFragment.class.getSimpleName();
    private static final int IMAGE_CAPTURE_REQUEST_ID = 100;
    private static final int SELECT_SINGLE_IMAGE_REQUEST_ID = 200;
    private static final int SELECT_IMAGES_REQUEST_ID = 300;
    private FragmentImagesBinding binding;
    private ImagesAdapter imagesAdapter;
    private RecyclerView recyclerView;
    private List<Image> mItems = new ArrayList<>();
    private File imageFile;
    private String imageFilePath;
    private String imageUriPath;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_images, container, false);
        setUpRecyclerView();
        binding.addImage.setOnClickListener(this::openImageMenu);
        return binding.getRoot();
    }

    private void setUpRecyclerView() {
        recyclerView = binding.recyclerview;
        imagesAdapter = new ImagesAdapter(mItems);
        recyclerView.setAdapter(imagesAdapter);
    }


    public void openImageMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(requireActivity(), binding.addImage);

        popupMenu.getMenu().add(0, 1, 0, "Take Picture");
        popupMenu.getMenu().add(0, 2, 0, "Select Image File");
        popupMenu.getMenu().add(0, 3, 0, "Select Images");

        popupMenu.setOnMenuItemClickListener(menuItem -> {

            switch (menuItem.getItemId()) {

                case 1:
                    Toast.makeText(requireActivity(), "Please Take Picture", Toast.LENGTH_SHORT).show();
                    takePicture();
                    break;

                case 2:
                    Toast.makeText(requireActivity(), " Select Image File", Toast.LENGTH_SHORT).show();
                    selectSingleImage();
                    break;

                case 3:
                    Toast.makeText(requireActivity(), " Select Images", Toast.LENGTH_SHORT).show();
                    selectImages();
                    break;
            }

            return true;
        });

        popupMenu.show();
    }

    private void selectImages() {
    }

    private void selectSingleImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent chooserIntent = Intent.createChooser(intent, "Select Image");
        startActivityForResult(chooserIntent, SELECT_SINGLE_IMAGE_REQUEST_ID);
    }

    private void takePicture() {
        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (captureImageIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            //create a new file and pass the uri as an output intent

            try {

                imageFile = FileUtil.createExternalStorageFile(requireActivity(), Constants.IMAGE_DIRECTORY);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (imageFile != null) {
                imageFilePath = imageFile.getAbsolutePath();

                Log.d(TAG, "image file path : " + imageFilePath);

                Uri imageUri = FileProvider.getUriForFile(
                        requireActivity(),
                        "com.example.files_practice.provider",
                        imageFile
                );

                imageUriPath = imageUri.toString();

                Log.d(TAG, "image uri path : " + imageUriPath);

                captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                startActivityForResult(captureImageIntent, IMAGE_CAPTURE_REQUEST_ID);
            }

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case IMAGE_CAPTURE_REQUEST_ID :
                    handleImageCaptureResult();
                    break;

                case SELECT_SINGLE_IMAGE_REQUEST_ID :
                    handleSingleImageSelection(data);
                    break;
            }

        }

        Toast.makeText(requireActivity(), "cancelled", Toast.LENGTH_SHORT).show();
    }

    private void handleSingleImageSelection(@Nullable Intent data) {
        if (data != null) {
            Uri selectedImageUri = data.getData();

            if (selectedImageUri != null) {
                try {
                    imageFile = FileUtil.createFileFromUri(requireActivity(), Constants.IMAGE_DIRECTORY, selectedImageUri);

                    imageFilePath = imageFile.getAbsolutePath();

                    Log.d(TAG, "image file path : " + imageFilePath);

                    Uri imageUri = FileProvider.getUriForFile(
                            requireActivity(),
                            "com.example.files_practice.provider",
                            imageFile
                    );

                    imageUriPath = imageUri.toString();

                    Log.d(TAG, "image uri path : " + imageUriPath);

                    Image image = new Image(imageFilePath, imageUriPath);

                    mItems.add(image);

                    imagesAdapter.setImages(mItems);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleImageCaptureResult() {
        Image image = new Image(imageFilePath, imageUriPath);

        mItems.add(image);

        imagesAdapter.setImages(mItems);

        Toast.makeText(requireActivity(), "image capture successful", Toast.LENGTH_SHORT).show();
    }


}