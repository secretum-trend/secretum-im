// Generated by view binder compiler. Do not edit!
package im.vector.application.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import im.vector.application.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDebugPermissionBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final Button audio;

  @NonNull
  public final Button camera;

  @NonNull
  public final Button cameraAudio;

  @NonNull
  public final Button contact;

  @NonNull
  public final CoordinatorLayout coordinatorLayout;

  @NonNull
  public final Button notification;

  @NonNull
  public final Button read;

  @NonNull
  public final TextView status;

  @NonNull
  public final Button write;

  private ActivityDebugPermissionBinding(@NonNull CoordinatorLayout rootView, @NonNull Button audio,
      @NonNull Button camera, @NonNull Button cameraAudio, @NonNull Button contact,
      @NonNull CoordinatorLayout coordinatorLayout, @NonNull Button notification,
      @NonNull Button read, @NonNull TextView status, @NonNull Button write) {
    this.rootView = rootView;
    this.audio = audio;
    this.camera = camera;
    this.cameraAudio = cameraAudio;
    this.contact = contact;
    this.coordinatorLayout = coordinatorLayout;
    this.notification = notification;
    this.read = read;
    this.status = status;
    this.write = write;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDebugPermissionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDebugPermissionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_debug_permission, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDebugPermissionBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.audio;
      Button audio = ViewBindings.findChildViewById(rootView, id);
      if (audio == null) {
        break missingId;
      }

      id = R.id.camera;
      Button camera = ViewBindings.findChildViewById(rootView, id);
      if (camera == null) {
        break missingId;
      }

      id = R.id.camera_audio;
      Button cameraAudio = ViewBindings.findChildViewById(rootView, id);
      if (cameraAudio == null) {
        break missingId;
      }

      id = R.id.contact;
      Button contact = ViewBindings.findChildViewById(rootView, id);
      if (contact == null) {
        break missingId;
      }

      CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;

      id = R.id.notification;
      Button notification = ViewBindings.findChildViewById(rootView, id);
      if (notification == null) {
        break missingId;
      }

      id = R.id.read;
      Button read = ViewBindings.findChildViewById(rootView, id);
      if (read == null) {
        break missingId;
      }

      id = R.id.status;
      TextView status = ViewBindings.findChildViewById(rootView, id);
      if (status == null) {
        break missingId;
      }

      id = R.id.write;
      Button write = ViewBindings.findChildViewById(rootView, id);
      if (write == null) {
        break missingId;
      }

      return new ActivityDebugPermissionBinding((CoordinatorLayout) rootView, audio, camera,
          cameraAudio, contact, coordinatorLayout, notification, read, status, write);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}