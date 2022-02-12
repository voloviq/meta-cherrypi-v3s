DESCRIPTION = "A console image for the Cherrypi V3s"
LICENSE = "MIT"

NETWORK_APP = " \
    openssh openssh-keygen openssh-sftp-server \
"

IMAGE_LINGUAS = "pl-pl"

inherit core-image

SYSTEM_TOOLS_INSTALL = " \
    i2c-tools \
    tzdata \
"

KERNEL_EXTRA_INSTALL = " \
    kernel-devicetree \
    kernel-modules \
 "

UTILITIES_INSTALL = " \
    coreutils \
    gdbserver \
    mtd-utils \
    ldd \
    libstdc++ \
    libstdc++-dev \
    openssh-sftp \
    resize-rootfs \
    gpio \
"

TSLIB = " \
    tslib \
    tslib-calibrate \
    tslib-conf \
    tslib-dev \
    tslib-tests \
"

IMAGE_INSTALL += " \
  ${NETWORK_APP} \
  ${SYSTEM_TOOLS_INSTALL} \
  ${UTILITIES_INSTALL} \
  ${TSLIB} \
  ${KERNEL_EXTRA_INSTALL} \
"

#Always add cmake to sdk
TOOLCHAIN_HOST_TASK_append = " nativesdk-cmake"
