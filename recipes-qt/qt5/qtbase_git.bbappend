PACKAGECONFIG:append = "accessibility fontconfig linuxfb tslib"
PACKAGECONFIG:remove = "examples tests"
PACKAGECONFIG:append = "xkbcommon"
QT_CONFIG_FLAGS += " -linuxfb "
