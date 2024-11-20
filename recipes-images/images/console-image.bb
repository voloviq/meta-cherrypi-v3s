DESCRIPTION = "A console image for the Cherry Pi Allwinner V3s Board"
LICENSE = "MIT"

IMAGE_LINGUAS = "pl-pl"

inherit core-image

IMAGE_FEATURES += "hwcodecs"

DISTRO_FEATURES:remove = "x11 wayland opengl opengles egl xcb linuxfb"

NETWORK_APP = " \
    openssh \
    openssh-keygen \
    openssh-sftp-server \
    openssh-sftp \
"

SYSTEM_TOOLS_INSTALL = " \
    i2c-tools \
    sysbench \
    rsync \
    minicom \
    can-utils \
    mpg123 \
    mpv \
    nginx \
    coreutils \
    gdbserver \
    ldd \
    tzdata \
    curl \
    ca-certificates \
    lsb-release \
    gnupg \
    parted \
    e2fsprogs \
    e2fsprogs-resize2fs \
"

CUSTOM_TOOLS_INSTALL = " \
    resize-rootfs \
    usb-gadget-dhcp \
"

CUSTOM_APP_INSTALL = " \
"

KERNEL_EXTRA_INSTALL = " \
    kernel-devicetree \
    kernel-modules \
    kernel-image-zimage \
"

LIB_PLUGINS_INSTALL = " \
    libstdc++ \
    libstdc++-dev \
    libgpiod \
    libgpiod-tools \
    alsa-lib \
    alsa-plugins \
"

AUDIO_LIBRARY_INSTALL = " \
    alsa-tools \
    alsa-utils \
    pulseaudio \
    portaudio-v19 \
"

IMAGE_INSTALL += " \
    ${NETWORK_APP} \  
    ${SYSTEM_TOOLS_INSTALL} \
    ${CUSTOM_TOOLS_INSTALL} \
    ${CUSTOM_APP_INSTALL} \
    ${KERNEL_EXTRA_INSTALL} \
    ${LIB_PLUGINS_INSTALL} \
    ${AUDIO_LIBRARY_INSTALL} \
"
