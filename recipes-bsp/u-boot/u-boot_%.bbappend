FILESEXTRAPATHS:prepend:sunxi := "${THISDIR}/files:"

DEPENDS:append:sunxi = " bc-native dtc-native swig-native python3-native flex-native bison-native "
DEPENDS:append:sun50i = " atf-sunxi "

COMPATIBLE_MACHINE:sunxi = "(sun4i|sun5i|sun7i|sun8i|sun50i)"

DEFAULT_PREFERENCE:sun4i = "1"
DEFAULT_PREFERENCE:sun5i = "1"
DEFAULT_PREFERENCE:sun7i = "1"
DEFAULT_PREFERENCE:sun8i = "1"
DEFAULT_PREFERENCE:sun50i = "1"

SRC_URI:append:sunxi = " \
        file://2022.01-001-cherry-pi-defconfig-sdcard-add.patch \
        file://2022.01-002-cherry-pi-dts-add.patch \
        file://2022.01-003-cherry-pi-nand-spi-option-add.patch \
        file://boot.cmd \
"

UBOOT_ENV_SUFFIX:sunxi = "scr"
UBOOT_ENV:sunxi = "boot"

EXTRA_OEMAKE:append:sunxi = ' HOSTLDSHARED="${BUILD_CC} -shared ${BUILD_LDFLAGS} ${BUILD_CFLAGS}" '

do_compile:append:sunxi() {
    ${B}/tools/mkimage -C none -A arm -T script -d ${WORKDIR}/boot.cmd ${WORKDIR}/${UBOOT_ENV_BINARY}
}
